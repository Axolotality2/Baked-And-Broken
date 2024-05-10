package BakeOrBreak.Item.Processor;

import BakeOrBreak.Item.DragIngredient;
import java.util.ArrayList;
import javafx.scene.image.ImageView;

public abstract class ItemReceiver extends ImageView {

    protected static final ArrayList<ItemReceiver> itemZones = new ArrayList<>();

    public abstract void put(DragIngredient ingredient);

    public static ArrayList<ItemReceiver> getItemZones() {
        return itemZones;
    }
}