package Controller;

import Util.DbWork;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.util.Optional;

public abstract class MyController<T> implements Initializable {

    protected DbWork dbWork = DbWork.getInstance();
    protected Stage dialogStage;
    protected Boolean forEdit;

    public void setItem(Optional<T> item){

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setForEdit(Boolean forEdit) {

    }

}
