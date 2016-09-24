package kr.swkang.bandaimallparser;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.swkang.bandaimallparser.utils.mvp.model.GunDamProductInfos;
import kr.swkang.bandaimallparser.utils.widgets.recyclerview.SwRecyclerView;

public class MainActivity
    extends AppCompatActivity
    implements MainActivityPresenter.View {
  private static final String TAG = MainActivity.class.getSimpleName();

  private MainActivityPresenter  presenter;
  private RvGundamProductAdapter adapter;
  private MenuItem               shareMenuItem;

  @BindView(R.id.main_toolbar)
  Toolbar        toolbar;
  @BindView(R.id.main_rv)
  SwRecyclerView rv;
  @BindView(R.id.main_loading_container)
  LinearLayout   loadingContainer;
  @BindView(R.id.main_emptyview_container)
  LinearLayout   emptyViewContainer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    setSupportActionBar(toolbar);

    rv.setHasFixedSize(true);
    rv.setLayoutManager(new LinearLayoutManager(this));

    adapter = new RvGundamProductAdapter(this, new ArrayList<GunDamProductInfos>());
    rv.setAdapter(adapter);

    rv.setEmptyView(emptyViewContainer);

    presenter = new MainActivityPresenter(this);
    presenter.retrieveProductList(1, false);

  }

  @OnClick(R.id.main_emptyview_btn_refresh)
  public void onClick(@NonNull View v) {
    presenter.retrieveProductList(1, true);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    if (menu != null) {
      getMenuInflater().inflate(R.menu.main, menu);
      this.shareMenuItem = menu.findItem(R.id.action_share);
      setShareMenuVisiblity(false);
    }
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_share) {
      shareGundamListText();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void updateLoadingState(boolean isLoading) {
    //emptyViewContainer.setVisibility(isLoading ? View.GONE : View.VISIBLE);
    loadingContainer.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    if (isLoading) setShareMenuVisiblity(false);
  }

  @Override
  public void updateDatas(@NonNull List<GunDamProductInfos> resultList, boolean isRefresh) {
    Log.d(TAG, "// updateDatas() // resultList.size() = " + resultList.size());
    adapter.setItem(resultList, isRefresh);
    setShareMenuVisiblity(adapter.getItemCount() > 0);
    Log.d(TAG, "// updateDatas() // adapter.size() = " + adapter.getItemCount());
  }

  public void setShareMenuVisiblity(boolean visiblity) {
    if (shareMenuItem != null) {
      shareMenuItem.setVisible(visiblity);
    }
  }

  @Override
  public void onError(String tag, String message) {
    Log.e(tag != null ? tag : MainActivity.class.getSimpleName(), message != null ? message : "Unknown Error.");
  }

  public void shareGundamListText() {
    if (adapter.getItemCount() > 0) {
      String shareBodyText = "\"이름\",\"이미지파일경로\",\"가격(원)\"\n";

      for (int i = 0; i < adapter.getItemCount(); i++) {
        GunDamProductInfos info = adapter.getItem(i);
        if (info != null) {
          shareBodyText += (info.toString() + "\n");
        }
      }

      Log.d(TAG, shareBodyText);

      Intent shareIntent = new Intent(Intent.ACTION_SEND);
      shareIntent.setType("text/csv");
      shareIntent.putExtra(Intent.EXTRA_SUBJECT, "");
      shareIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
      startActivity(Intent.createChooser(shareIntent, "공유 하기"));
    }

  }

}
