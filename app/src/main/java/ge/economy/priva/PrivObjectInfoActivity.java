package ge.economy.priva;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

        TextView textViewName = (TextView) findViewById(R.id.f_detail_name);
        textViewName.setText(mPrivObject.getName());
        TextView textViewAddress = (TextView) findViewById(R.id.f_detail_address);
        textViewAddress.setText(mPrivObject.getAddress());
        TextView textViewCadCode = (TextView) findViewById(R.id.f_detail_cadCode);
        textViewCadCode.setText(mPrivObject.getCadCode());
        TextView textViewGoogle = (TextView) findViewById(R.id.f_detail_google);
        textViewGoogle.setText(mPrivObject.getGoogleMapLink());
        TextView textViewReestri = (TextView) findViewById(R.id.f_detail_reestri);
        textViewReestri.setText(mPrivObject.getReestriLink());
        TextView textViewCadastral = (TextView) findViewById(R.id.f_detail_cadastral);
        textViewCadastral.setText(mPrivObject.getCadastrMapLink());
        TextView textViewAuctionNumber = (TextView) findViewById(R.id.f_detail_auctionNumber);
        textViewAuctionNumber.setText(mPrivObject.getAuctionOrderNumber());
        TextView textViewAuctionDate = (TextView) findViewById(R.id.f_detail_auctionDate);
        textViewAuctionDate.setText(mPrivObject.getAuctionStartEndDate());
        TextView textViewDisposalForm = (TextView) findViewById(R.id.f_detail_disposalForm);
        textViewDisposalForm.setText(mPrivObject.getDisposalForm());
        TextView textViewWinnerIdNumber = (TextView) findViewById(R.id.f_detail_winnerIdNumber);
        textViewWinnerIdNumber.setText(mPrivObject.getWinnerIdNumber());
        TextView textViewWinnerName = (TextView) findViewById(R.id.f_detail_winnerName);
        textViewWinnerName.setText(mPrivObject.getWinnerName());
        TextView textViewPrice = (TextView) findViewById(R.id.f_detail_price);
        textViewPrice.setText(mPrivObject.getPrice());
        TextView textViewAppNumber = (TextView) findViewById(R.id.f_detail_appNumber);
        textViewAppNumber.setText(mPrivObject.getAppNumber());
        TextView textViewObligation = (TextView) findViewById(R.id.f_detail_obligation);
        textViewObligation.setText(mPrivObject.getObligation());

        mPrivObjectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPrivObject != null && mPrivObject.getImages() != null) {
                    PrivObjectPhotoDialog.images=mPrivObject.getImages();
                    PrivObjectPhotoDialog dialog=new PrivObjectPhotoDialog();
                    dialog.show(getFragmentManager(),"");
                }
            }
        });
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
