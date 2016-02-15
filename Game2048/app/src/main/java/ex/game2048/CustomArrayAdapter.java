package ex.game2048;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ilya on 2/14/2016.
 */
public class CustomArrayAdapter extends ArrayAdapter<String> {

    private String[] objects;
    private Context context;

    public CustomArrayAdapter(Context context, int resourceId,
                              String[] objects) {
        super(context, resourceId, objects);
        this.objects = objects;
        this.context = context;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=(LayoutInflater) context.getSystemService(  Context.LAYOUT_INFLATER_SERVICE );
        View row = inflater.inflate(R.layout.spinner_row, parent, false);
        TextView label= (TextView)row.findViewById(R.id.textView);
        label.setText(objects[position]);

        return row;
    }

}