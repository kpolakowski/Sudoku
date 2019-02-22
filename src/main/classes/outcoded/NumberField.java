package outcoded;

import javafx.scene.control.TextField;

public final class NumberField extends TextField{
    private int index,value;

    public NumberField(int index, int value ) {
        this.setIndex(index);
        this.setValue(value);
        this.setEnabled(false);
    }
    
    public int getValue(){return this.value;}
    public int getIndex(){return this.index;}
    
    public void setValue(int value){
        this.value=value;
        this.setText(value == 0 ? " ": Integer.toString(this.value));
    }
    public void setIndex(int index){
        this.index=index;
    }
    
    public void setEnabled(boolean enabled){
        this.setEditable(enabled);
    }
    
    public void increase(){
        this.setValue(++value % 10);
    }
}
