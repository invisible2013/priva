package ge.economy.priva;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import ge.economy.priva.data.PrivObject;
import ge.economy.priva.data.SearchObject;


public class MainActivity extends Activity {
    private SearchObject mSearchObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void search(View view){

        EditText personEdit=(EditText)findViewById(R.id.f_person);
        EditText addressEdit=(EditText)findViewById(R.id.f_address);
        EditText aNumberEdit=(EditText)findViewById(R.id.f_auction_number);
        EditText cadCodeEdit=(EditText)findViewById(R.id.f_cadcode);
        mSearchObject=new SearchObject();
        mSearchObject.setPerson(personEdit.getText().toString());
        mSearchObject.setAddress(addressEdit.getText().toString());
        mSearchObject.setCadCode(cadCodeEdit.getText().toString());
        mSearchObject.setAuctionNumber(aNumberEdit.getText().toString());

        makeCall(new Callback() {
            @Override
            public void onObjectsReceive(List<PrivObject> list) {
                ResultActivity.mPrivObjects = list;
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                startActivity(intent);

            }
        });

    }

    private void makeCall(final Callback callback){
        RequestParams params=new RequestParams();
        params.add("page","06a943c59f33a34bb5924aaf72cd2995");
        if(!mSearchObject.getPerson().isEmpty()){
            params.add("org",mSearchObject.getPerson());
        }
        if(!mSearchObject.getAddress().isEmpty()){
            params.add("addr",mSearchObject.getAddress());
        }
        if(!mSearchObject.getCadCode().isEmpty()){
            params.add("cadcod",mSearchObject.getCadCode());
        }
        if(!mSearchObject.getAuctionNumber().isEmpty()){
            params.add("ean",mSearchObject.getAuctionNumber());
        }

        AsyncHttpClient rr = new AsyncHttpClient();

        rr.get("http://privatization.ge/", params, new TextHttpResponseHandler() {


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                int a = 10;
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Document document = Jsoup.parse(responseString);

                List<PrivObject> privObjects = new ArrayList<PrivObject>();
                Elements objects = document.getElementsByClass("tbSearch");
                if (objects != null && !objects.isEmpty()) {
                    Elements trs = objects.get(0).getElementsByTag("tr");
                    if (trs.size() > 5) {
                        for (int i = 0; i < trs.size(); i++) {
                            if (i < 5) {
                                continue;
                            }
                            Elements tds = trs.get(i).getElementsByTag("td");
                            PrivObject _obj = new PrivObject();
                            _obj.setAddress(tds.get(0).getElementsByTag("p").get(0).text());
                            _obj.setHref(tds.get(0).getElementsByTag("p").first().attr("onclick"));
                            _obj.setCadCode(tds.get(0).getElementsByTag("p").get(1).text());
                            _obj.setSaleType(tds.get(2).getElementsByTag("p").get(0).text());
                            _obj.setDate(tds.get(2).getElementsByTag("p").get(1).text());
                            _obj.setPrice(tds.get(2).getElementsByTag("p").get(2).text());
                            _obj.setWinner(tds.get(4).getElementsByTag("p").text());
                            _obj.setStatusClass(tds.get(4).getElementsByTag("p").attr("class"));
                            _obj.setObligation(tds.get(6).getElementsByTag("p").text());
                            privObjects.add(_obj);
                        }
                    }
                }
                callback.onObjectsReceive(privObjects);

            }
        });

    }

    interface Callback{
        void onObjectsReceive(List<PrivObject> list);
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
