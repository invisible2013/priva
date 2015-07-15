package ge.economy.priva;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.ArrayList;
import java.util.List;

import ge.economy.priva.data.KeyValue;


public class PrivObjectImageActivity extends Activity {
    public static List<String> mPrivObjectImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_priv_object_image);

        ListView listView = (ListView) findViewById(R.id.p_priv_obeject_image_list);
        PrivObjectImageAdapter privObjectImageAdapter = new PrivObjectImageAdapter(getApplicationContext(), 0, new ArrayList<String>());
        listView.setAdapter(privObjectImageAdapter);
        if(mPrivObjectImages!=null){
            privObjectImageAdapter.addAll(mPrivObjectImages);
        }
    }

    class PrivObjectImageAdapter extends ArrayAdapter<String> {

        private Context mContext;
        private List<String> mObjects;

        public PrivObjectImageAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            mContext = context;
            mObjects = objects;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            String st = mObjects.get(position);
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.view_priv_object_image_item, parent, false);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.p_priv_object_image);
            UrlImageViewHelper.setUrlDrawable(imageView, st);

            return rowView;
        }
    }


}
