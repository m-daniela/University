package View;

import Controller.Controller;
import Exceptions.ExceptionCustom;

import java.io.FileNotFoundException;
import java.io.IOException;

public class RunExample extends Command {
    private Controller ctr;
    public RunExample(String key, String description, Controller ctr) {
        super(key, description);
        this.ctr = ctr;
    }

    @Override
    public void execute() {
        try{
            ctr.allStep();
        } catch (ExceptionCustom | IOException e) {
            e.printStackTrace();
        }
    }
}
