package learn.accomadation;

import learn.accomadation.data.DataAccessException;
import learn.accomadation.data.GuestFileRepository;
import learn.accomadation.models.Guest;
import learn.accomadation.ui.Controller;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import java.util.List;
@ComponentScan
@PropertySource("classpath:data.properties")
public class App {
    public static void main(String[] args) throws DataAccessException {
        ApplicationContext context = new AnnotationConfigApplicationContext(App.class);

        Controller controller = context.getBean(Controller.class);

        controller.run();
    }
}
