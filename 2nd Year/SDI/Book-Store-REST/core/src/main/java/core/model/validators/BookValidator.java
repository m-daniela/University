package core.model.validators;


import core.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Optional;

@Component
public class BookValidator implements Validator<Book>  {
    private boolean checkNull(String stringToBeChecked){
        return stringToBeChecked.equals("") || stringToBeChecked.equals(" ");
    }
    private Optional<Boolean> validName(String name){
        // Name can't contain numbers, special characters
        // If multiple authors, they are separate by ';'
        return Optional.of(name.contains("1234567890=+_[]{}()~`!@#$%^&*<>,/?"));
    }
    private boolean isSerialNumberValid(String number){//no spaces in the serial number and no special characters
        String[] arrOfStr = number.split(" ", 3);
        return arrOfStr.length==1 && !number.contains("!,.:;?/(){}[]@#$%^&*_+=-");
    }

    @Override
    public void validate(Book entity) throws ValidatorException {
        if(entity == null) {
            throw new ValidatorException("Book cannot be null");
        }

        if(entity.getSerialNumber().equals("0") || !isSerialNumberValid(entity.getSerialNumber())) {
            throw new ValidatorException("Please enter a valid serial number.");
        }

        if(checkNull(entity.getTitle()))
            throw new ValidatorException("Please enter a valid name.");

        if(checkNull(entity.getAuthor()))
            throw new ValidatorException("Please enter a valid author name.");

        if(entity.getYear() < 0 || entity.getYear() > Calendar.getInstance().get(Calendar.YEAR))
            throw new ValidatorException("Please enter a valid year");

        if(entity.getPrice() <= 0)
            throw new ValidatorException("Please enter a valid price");

        



    }
}
