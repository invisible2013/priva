package ge.economy.priva.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ge.economy.priva.R;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

public class PrivObjectPhotoDialog extends DialogFragment {

    public static List<String> images;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.view_priv_object_images, null);

        ListView imagesList = (ListView) linearLayout.findViewById(R.id.p_priv_obeject_images);
        PrivObjectImagesAdapter adapter = new PrivObjectImagesAdapter(getActivity(), 0, new ArrayList<String>());
        imagesList.setAdapter(adapter);
        adapter.addAll(images);
        builder.setView(linearLayout);
        return builder.create();
    }

class PrivObjectImagesAdapter extends ArrayAdapter<String>{
    private Context mContext;
    private List<String> mPrivObjects;

    public  PrivObjectImagesAdapter(Context context,int resource, List<String> t){
        super(context,resource,t);
        this.mContext = context;
        this.mPrivObjects = t;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String t = mPrivObjects.get(position);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.view_priv_object_image_item, parent, false);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.p_priv_obeject_image);
        UrlImageViewHelper.setUrlDrawable(imageView, t);
        return rowView;
    }
}



}
