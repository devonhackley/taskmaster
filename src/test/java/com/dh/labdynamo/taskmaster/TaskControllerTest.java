package com.dh.labdynamo.taskmaster;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TaskmasterApplication.class)
@WebAppConfiguration
@ActiveProfiles("local")
public class TaskControllerTest {
    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    TaskRepository taskRepository;

    @Before
    public void setup() throws Exception {
        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

        CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(Task.class);

        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

        dynamoDBMapper.batchDelete((List<Task>)taskRepository.findAll());
    }

    @Test
    public void readWriteTestCase() {
        Task task = new Task("Task 1", "This is the first task");
        taskRepository.save(task);

        List<Task> result = (List<Task>) taskRepository.findAll();

        assertTrue("Not empty", result.size() > 0);
        assertTrue("Contains item with expected cost", result.get(0).getTitle().equals("Task 1"));
    }
}
