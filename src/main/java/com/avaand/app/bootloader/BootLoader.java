package com.avaand.app.bootloader;

import com.avaand.app.async.AsynchronousExecutor;
import com.avaand.app.cache.impl.TrackerServiceImpl;
import com.avaand.app.cache.model.Tracker;
import com.avaand.app.domain.*;
import com.avaand.app.dto.PersonDto;
import com.avaand.app.event.StartupEvent;
import com.avaand.app.lifecycle.LifeCycle;
import com.avaand.app.mapper.EmployeeMapper;
import com.avaand.app.mapper.PersonMapper;
import com.avaand.app.model.BankService;
import com.avaand.app.processor.tag.RandomInt;
import com.avaand.app.repository.PetRepository;
import com.avaand.app.repository.PersonRepository;
import com.avaand.app.repository.UserRepository;
import com.avaand.app.service.FoodType;
import com.avaand.app.service.ReadableService;
import com.avaand.app.service.Waiter;
import com.avaand.app.system.props.ConfigProperties;
import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.java.Log;
import org.jetbrains.annotations.NotNull;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.aspectj.AnnotationBeanConfigurerAspect;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.*;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.env.Environment;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.*;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Log
@Component
public class BootLoader implements CommandLineRunner, ApplicationContextAware {

    @RandomInt(min = 0, max = 10)
    private int randomInt;
    private final Waiter waiter;
    private final ConfigProperties configProperties;
    private ApplicationContext context;
    private final MessageSource messageSource;
    private final ConversionService conversionService;
    private final Validator validator;
    private final ApplicationEventPublisher publisher;
    private final UserRepository userRepository;

    //private final StorageService storageService;

    private final Environment environment;

    private final PersonRepository personRepository;

    private final PasswordEncoder passwordEncoder;

    private final PersonMapper personMapper = Mappers.getMapper(PersonMapper.class);

    private final EmployeeMapper employeeMapper = Mappers.getMapper(EmployeeMapper.class);

    @PostConstruct
    public void onInit(){
        log.info("On Create Method");
    }

    @Autowired
    public BootLoader(Waiter waiter,
                      ConfigProperties configProperties,
                      ApplicationContext context,
                      MessageSource messageSource,
                      ConversionService conversionService,
                      Validator validator,
                      ApplicationEventPublisher publisher,
                      UserRepository userRepository, Environment environment, PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.waiter = waiter;
        this.configProperties = configProperties;
        this.context = context;
        this.messageSource = messageSource;
        this.conversionService = conversionService;
        this.validator = validator;
        this.publisher = publisher;
        this.userRepository = userRepository;
        this.environment = environment;
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info(messageSource.getMessage("welcome",null, Locale.US));

        try {
            log.info(waiter.deliverFood(FoodType.PIZZA));
        }catch (Exception e){
            log.info(e.getMessage());
        }

        log.info(configProperties.getUsername());
        var lifecycleA = context.getBean(LifeCycle.class); // calls new object prototype
        var lifeCycleB = context.getBean(LifeCycle.class); // calls new object prototype
        log.info(String.valueOf(lifecycleA.equals(lifeCycleB)));

        // Using Loop to demonstrate CacheManager
        var trackerService = context.getBean(TrackerServiceImpl.class);
        for (int i = 0; i < 10; i++) {
            List<Tracker> trackers = trackerService.getTrackers();
            log.info("Loop Index -> " + i);
        }

        // Todo: Cache Manager will call the data from the cached data source. Makes it more efficient
        var tracker = new Tracker(1, null);
        for (int i = 0; i < 1000; i++) {
            Tracker serviceTracker = trackerService.findTracker(tracker);
        }

        trackerService.findTracker(tracker);

        var bankService = context.getBean(BankService.class);
        bankService.deposit(100);

        var asynchronousExecutor = context.getBean(AsynchronousExecutor.class);
        var result = asynchronousExecutor.asyncExecutionWithReturnType("Hello World");
        log.info(result.get());
        asynchronousExecutor.asyncExecution();

        /*
        var user = conversionService.convert("1,partha.com,12345", User.class);
        if (user != null){
            log.info(user.toString());
        }*/

        /*
        var violations = validator.validate(user);
        violations.iterator().forEachRemaining(violation -> {
            log.info(violation.getMessage());
        });*/

        var readableService = context.getBean(ReadableService.class);
        String value = readableService.sayHello();
        log.info("ReadableService : " + value);

        DirectChannel channel = context.getBean("inputChannel", DirectChannel.class);
        DirectChannel outputChannel = context.getBean("outputChannel", DirectChannel.class);

        outputChannel.subscribe(message -> log.info("Message : " + message.getPayload()));

        Message<String> message = MessageBuilder.withPayload("Mike")
                .setReplyChannel(outputChannel)
                .build();

        channel.send(message);
        channel.send(message);

        MessagingTemplate template = new MessagingTemplate(channel);
        template.setReceiveTimeout(10);
        Message<?> output = template.sendAndReceive(message);

        if (output != null){
            log.info(String.valueOf(output.getPayload()));
        }
        log.info("Random Int: " + randomInt);
        publisher.publishEvent(new StartupEvent<>(this, "Startup"));


        //storageService.upload();
        //storageService.streamUpload();

        Person p = new Person();
        p.setUserId(UUID.randomUUID().toString());
        p.setUsername("Partha Sutradhar");

        Employee employee = employeeMapper.toEmployee(p);
        log.warning("Employee " + employee);

        PersonDto personDto = personMapper.toPersonDto(p);
        log.warning("Person DTO " + personDto);

        Person mapperPerson = personMapper.toPerson(personDto);
        log.warning("Person Mapper " + mapperPerson);

        Person person = createUser(personRepository);
        System.out.println(new Gson().toJson(person));

        personRepository.findAll();

        Person u = updateUser(person);
        System.out.println(new Gson().toJson(u));

        PetRepository repository = context.getBean(PetRepository.class);
        repository.save(new Pet());
        repository.save(new Pet());
        System.out.println(repository.findAll().size());

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("partha");
        userEntity.setPassword(passwordEncoder.encode("12345678"));
        userRepository.save(userEntity);

    }

    private Person updateUser(Person person) {
        System.out.println(person.getUserId());
        Person u = personRepository.findById(person.getUserId()).get();
        u.setUsername("false@gmail.com");
        u = personRepository.saveAndFlush(u);
        return u;
    }

    private Person createUser(PersonRepository personRepository) {
        Person person = new Person();
        person.setUsername("Hello World");
        return personRepository.save(person);
    }

    @Bean
    public AnnotationBeanConfigurerAspect annotationBeanConfigurerAspect(){
        return AnnotationBeanConfigurerAspect.aspectOf();
    }

    @PreDestroy
    public void onDispose(){
        log.info("On Destroy Method");
    }

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

}
