package dashboard;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTableCellCallback<T> implements Callback<TableColumn<T, Date>, TableCell<T, Date>> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy - hh:mm");

    @Override
    public TableCell<T, Date> call(TableColumn<T, Date> param) {
        return new TableCell<T, Date>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(dateFormat.format(item));
                } else {
                    setText(null);
                }
            }
        };
    }
}
