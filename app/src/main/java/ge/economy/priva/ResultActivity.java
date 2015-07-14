package ge.economy.priva;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import ge.economy.priva.data.PrivObject;
import ge.economy.priva.services.PURL;


public class ResultActivity extends Activity {

    private ListView mObjects;
    private ResultAdapter mObjectsAdapter;
    public static List<PrivObject> mPrivObjects;
    private PrivObject mSelectedObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mObjects = (ListView) findViewById(R.id.f_search_result_list);
        mObjectsAdapter = new ResultAdapter(getApplicationContext(), 0, new ArrayList<PrivObject>());
        mObjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3) {
                mSelectedObject = (PrivObject) adapter.getItemAtPosition(position);
                makeCall(new Callback() {
                    @Override
                    public void onObjectsReceive(PrivObject privObject) {
                        PrivObjectInfoActivity.mPrivObject = privObject;
                        Intent intent = new Intent(ResultActivity.this, PrivObjectInfoActivity.class);
                        startActivity(intent);
                    }
                });

            }
        });
        mObjects.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int i, long arg3) {
                return true;
            }
        });
        mObjects.setAdapter(mObjectsAdapter);
        if (mPrivObjects != null) {
            mObjectsAdapter.addAll(mPrivObjects);
        }
    }

    private void makeCall(final Callback callback) {
        RequestParams params = new RequestParams();
        // params.add("page","06a943c59f33a34bb5924aaf72cd2995");
        String splited = mSelectedObject.getHref().substring(24, mSelectedObject.getHref().length() - 1);
        AsyncHttpClient rr = new AsyncHttpClient();

        rr.get(PURL.PRIVA_BASE_URL + splited, null, new TextHttpResponseHandler() {


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                int a = 10;
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Document document = Jsoup.parse(responseString);

                PrivObject _obj = new PrivObject();
                Elements objects = document.getElementsByClass("inside_table");
                if (objects != null && !objects.isEmpty()) {
                    Elements table = objects.get(0).getElementsByTag("table");
                    Elements trs = table.get(1).getElementsByTag("tr");

                    int i = 1;
                    _obj.setName(trs.get(i).getElementsByTag("td").get(1).text());
                    _obj.setAddress(trs.get(i + 1).getElementsByTag("td").get(1).text());
                    _obj.setCadCode(trs.get(i + 2).getElementsByTag("td").get(1).text());
                    _obj.setReestriLink(trs.get(i + 3).getElementsByTag("td").get(1).text());
                    if (trs.get(5).getElementsByTag("td").get(0).text().equals("საკადასტრო რუკა (ლინკი")) {
                        _obj.setCadastrMapLink(trs.get(5).getElementsByTag("td").get(1).text());
                        if (trs.get(6).getElementsByTag("td").get(0).text().equals("ობიექტის Google კოორდინატები (ლინკი")) {
                            _obj.setGoogleMapLink(trs.get(6).getElementsByTag("td").get(1).text());
                            i++;
                        }
                    } else {
                        _obj.setGoogleMapLink(trs.get(5).getElementsByTag("td").get(1).text());
                    }
                    _obj.setAuctionOrderNumber(trs.get(i + 7).getElementsByTag("td").get(1).text());
                    _obj.setAuctionStartEndDate(trs.get(i + 8).getElementsByTag("td").get(1).text());
                    _obj.setDisposalForm(trs.get(i + 9).getElementsByTag("td").get(1).text());
                    if (trs.get(i + 10).getElementsByTag("td").get(0).text().equals("სარგებლობაში გადაცემის ვადა")) {
                        _obj.setDisposalTerm(trs.get(i + 10).getElementsByTag("td").get(1).text());
                        i++;
                    }
                    _obj.setWinnerIdNumber(trs.get(i + 12).getElementsByTag("td").get(1).text());
                    _obj.setWinnerName(trs.get(i + 13).getElementsByTag("td").get(1).text());
                    _obj.setPrice(trs.get(i + 14).getElementsByTag("td").get(1).text());
                    _obj.setAppNumber(trs.get(i + 15).getElementsByTag("td").get(1).text());
                    _obj.setObligation(trs.get(i + 16).getElementsByTag("td").get(1).text());
                    _obj.setOtherObligation(trs.get(i + 19).getElementsByTag("td").get(1).text());
                    _obj.setBankRequisites(trs.get(i + 25).getElementsByTag("td").get(1).text());
                    _obj.setNote(trs.get(i + 27).getElementsByTag("td").get(0).text());
                    _obj.setNoteDescription(trs.get(i + 27).getElementsByTag("td").get(0).getElementsByTag("p").get(1).text());

                    Elements imgs = table.get(2).getElementById("wrapper1").getElementsByTag("img");
                    List<String> images=new ArrayList<String>();
                    for(Element e:imgs){
                        images.add(PURL.PRIVA_BASE_URL + e.attr("src"));
                    }
                    _obj.setImages(images);
                }
                callback.onObjectsReceive(_obj);

            }
        });

    }

    interface Callback {
        void onObjectsReceive(PrivObject privObject);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

class ResultAdapter extends ArrayAdapter<PrivObject> {

    private Context mContext;
    private List<PrivObject> mPrivObjects;

    public ResultAdapter(Context context, int resource, List<PrivObject> privObjects) {
        super(context, resource, privObjects);
        this.mContext = context;
        this.mPrivObjects = privObjects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        PrivObject privObject = getItem(position);

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.view_search_result_item, parent, false);

        ((TextView) rowView.findViewById(R.id.f_object_address)).setText(privObject.getAddress());
        ((TextView) rowView.findViewById(R.id.f_object_cadCode)).setText(privObject.getCadCode());
        ((TextView) rowView.findViewById(R.id.f_object_date)).setText(privObject.getDate());
        ((TextView) rowView.findViewById(R.id.f_object_price)).setText(privObject.getPrice());
        ((TextView) rowView.findViewById(R.id.f_object_winner)).setText(privObject.getWinner());
        if (privObject.getStatusClass().equals("clr_green")) {
            ((TextView) rowView.findViewById(R.id.f_object_winner)).setTextColor(inflater.getContext().getResources().getColor(R.color.clr_green));
        } else if (privObject.getStatusClass().equals("clr_carrot")) {
            ((TextView) rowView.findViewById(R.id.f_object_winner)).setTextColor(inflater.getContext().getResources().getColor(R.color.clr_carrot));
        } else if (privObject.getStatusClass().equals("clr_red")) {
            ((TextView) rowView.findViewById(R.id.f_object_winner)).setTextColor(inflater.getContext().getResources().getColor(R.color.clr_red));
        }
        return rowView;
    }
}