package com.example.baetube.Spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.baetube.R;

import java.util.ArrayList;

public class SpinnerDropdownAdapter extends ArrayAdapter<SpinnerItem>
{

    private Context context;
    private ArrayList<SpinnerItem> list = null;
    private boolean isSimple;

    public SpinnerDropdownAdapter( Context context, int resource, ArrayList<SpinnerItem> list, boolean isSimple)
    {
        super(context, resource);
        this.context = context;
        this.list = list;
        this.isSimple = isSimple;
    }


    @Override
    public View getView(int position, @Nullable View convertView,  ViewGroup parent)
    {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView,  ViewGroup parent)
    {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        SpinnerItem item = list.get(position);

        if(isSimple)
        {
            convertView = layoutInflater.inflate(R.layout.spinner_dropdown_simple, parent, false);
            TextView title = convertView.findViewById(R.id.spinner_dropdown_text_title);
            title.setText(item.getTitle());

            return convertView;
        }

        convertView = layoutInflater.inflate(R.layout.spinner_dropdown, parent, false);

        ImageView icon = convertView.findViewById(R.id.spinner_dropdown_image_icon);
        TextView title = convertView.findViewById(R.id.spinner_dropdown_text_title);
        TextView description = convertView.findViewById(R.id.spinner_dropdown_text_description);

        icon.setImageResource(item.getImage());
        title.setText(item.getTitle());
        description.setText(item.getDescription());

        return convertView;
    }

    @Override
    public int getCount()
    {
        return list.size();
    }


}
