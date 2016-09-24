package kr.swkang.bandaimallparser;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kr.swkang.bandaimallparser.utils.common.SwObservable;
import kr.swkang.bandaimallparser.utils.mvp.BasePresenter;
import kr.swkang.bandaimallparser.utils.mvp.BaseView;
import kr.swkang.bandaimallparser.utils.mvp.model.GunDamProductInfos;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author KangSung-Woo
 * @since 2016-09-23
 */
public class MainActivityPresenter
    extends BasePresenter {
  private static final String TAG = MainActivityPresenter.class.getSimpleName();
  private View view;

  public MainActivityPresenter(@NonNull View activity) {
    this.view = activity;
  }

  public void retrieveProductList(@IntRange(from = 1) int page, final boolean isRefresh) {
    // TODO : need category code, page number
    final String targetUrl = "http://www.bandaimall.co.kr/display/category.do?method=sgroup&" +
        "category_code=2010102000" +
        "&depth=2&target_name=smenu&menu_cnt=10&on_id=mmenu_2010000000_&mmenu_cnt=10&smenu_cnt=10&orderType=2&" +
        "currentPage=" + (String.valueOf(page)) +
        "&soldOutFlag=0&pagePerCount=";

    final SwObservable observable = new SwObservable(
        this,
        Observable.create(
            new Observable.OnSubscribe<List<GunDamProductInfos>>() {
              @Override
              public void call(Subscriber<? super List<GunDamProductInfos>> subscriber) {
                try {
                  Document doc = Jsoup.connect(targetUrl).get();
                  //Log.w(TAG, doc.outerHtml());

                  ArrayList<GunDamProductInfos> list = new ArrayList<GunDamProductInfos>();

                  Elements productsByLi = doc.select(".newar_list ul li");
                  //Log.w(TAG, "////////// productsByLi.size() = " + productsByLi.size());

                  for (Element e : productsByLi) {
                    GunDamProductInfos gunDam = new GunDamProductInfos();

                    Elements imgList = e.select(".newar_img a img");
                    //Log.w(TAG, "/////////////// imgList.size() = " + imgList.size());
                    for (Element imgElement : imgList) {
                      if (imgElement.hasAttr("src")) {
                        gunDam.setImagePath(imgElement.attr("src"));
                      }
                      if (imgElement.hasAttr("alt")) {
                        gunDam.setName(imgElement.attr("alt"));
                      }
                    }

                    Elements aTagList = e.select(".newar_txt02 strong");
                    //Log.w(TAG, "/////////////// aTagList.size() = " + aTagList.size());
                    for (Element aTagElement : aTagList) {
                      gunDam.setPriceOfKorea(aTagElement.text());
                    }

                    list.add(gunDam);
                    Log.d(TAG, "/////////////// gunDam infos = " + gunDam.toString());
                  }
                  subscriber.onNext(list);

                } catch (IOException ioe) {
                  subscriber.onError(ioe);
                }
              }
            }
        ).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
    );
    observable.subscribe(
        new Subscriber<List<GunDamProductInfos>>() {
          @Override
          public void onCompleted() {
            view.updateLoadingState(false);
          }

          @Override
          public void onError(Throwable e) {
            view.onError(TAG, e != null ? e.getMessage() : "UNKNOWN ERROR");
            view.updateLoadingState(false);
          }

          @Override
          public void onNext(List<GunDamProductInfos> result) {
            view.updateDatas(result, isRefresh);
            view.updateLoadingState(false);
          }
        }
    );
    view.updateLoadingState(true);
  }

  public interface View
      extends BaseView {
    void updateLoadingState(boolean isLoading);

    void updateDatas(@NonNull List<GunDamProductInfos> resultList, boolean isRefresh);
  }

}
