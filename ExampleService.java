import org.springframework.stereotype.Service;

@Service
public class ExampleService {

    @LogExecutionTime
    public void serve() throws InterruptedException {
        // Simulate a method execution
        Thread.sleep(2000);
    }

    public static void main(String[] args) throws InterruptedException {
        ExampleService service = new ExampleService();
        service.serve();
    }
}
