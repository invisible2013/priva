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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ge.economy.priva.data.KeyValue;
import ge.economy.priva.data.PrivObject;
import ge.economy.priva.fragments.PrivObjectPhotoDialog;


public class PrivObjectInfoActivity extends Activity {
    public static PrivObject mPrivObject;
    private RelativeLayout mPrivObjectPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_priv_object_info);

        mPrivObjectPhoto = (RelativeLayout) findViewById(R.id.p_priv_obeject_photo);
        mPrivObjectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPrivObject != null && mPrivObject.getImages() != null) {
                    PrivObjectPhotoDialog.images = mPrivObject.getImages();
                    PrivObjectPhotoDialog dialog = new PrivObjectPhotoDialog();
                    dialog.show(getFragmentManager(), "");
                }
            }
        });

        ListView listView = (ListView) findViewById(R.id.p_priv_obeject_items);
        PrivObjectInfoAdapter privObjectInfoAdapter = new PrivObjectInfoAdapter(PrivObjectInfoActivity.this, 0, new ArrayList<KeyValue>());
        listView.setAdapter(privObjectInfoAdapter);

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        for (Map.Entry<String, String> entry : mPrivObject.getAttributes().entrySet()) {
            keyValues.add(new KeyValue(entry.getKey(),entry.getValue()));
        }

        privObjectInfoAdapter.addAll(keyValues);


    }

    class PrivObjectInfoAdapter extends ArrayAdapter<KeyValue> {

        private Context mContext;
        private List<KeyValue> mObjects;

        public PrivObjectInfoAdapter(Context context, int resource, List<KeyValue> objects) {
            super(context, resource, objects);
            mContext = context;
            mObjects = objects;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            KeyValue k = mObjects.get(position);
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.view_priv_object_info_item, parent, false);

            TextView key = ((TextView) rowView.findViewById(R.id.p_priv_object_name));
            key.setText(k.getKey());

            TextView value = ((TextView) rowView.findViewById(R.id.p_priv_object_value));
            value.setText(k.getValue());

            return rowView;
        }
    }


}


