package com.ztkj.victe.ui.baidu

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.baidu.location.BDLocation
import com.baidu.location.BDLocationListener
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.core.RouteLine
import com.baidu.mapapi.search.core.SearchResult
import com.baidu.mapapi.search.geocode.*
import com.baidu.mapapi.search.route.*
import com.baidu.mapapi.utils.route.BaiduMapRoutePlan
import com.baidu.mapapi.utils.route.RouteParaOption.EBusStrategyType
import com.baidu.mapapi.utils.route.RouteParaOption
import com.ztkj.victe.R
import com.ztkj.victe.ui.base.LazyFragment
import kotlinx.android.synthetic.main.frag_baidu.*


/**
 * Created by 13526 on 2018/3/22.
 */
class BaiDuFragment : LazyFragment(), OnGetRoutePlanResultListener, OnGetGeoCoderResultListener {

    private lateinit var mBaiduMap: BaiduMap;
    private lateinit var mLocClient: LocationClient;
    internal var isFirstLoc = true // 是否首次定位
    private var locData: MyLocationData? = null
    internal var mCurrentMarker: BitmapDescriptor? = null
    private var mCurrentDirection = 0
    private var mCurrentMode: MyLocationConfiguration.LocationMode? = null
    private var mSearch: RoutePlanSearch? = null    // 搜索模块，也可去掉地图模块独立使用
    private var mGeoCoderSearch: GeoCoder? = null // 搜索模块，也可去掉地图模块独立使用
    private var nowResultdrive: DrivingRouteResult? = null
    private var nowSearchType: Int = 0;
    internal var nodeIndex = -1 // 节点索引,供浏览节点时使用
    internal var hasShownDialogue = false
    private lateinit var route: RouteLine<*>;
    private lateinit var routeOverlay: OverlayManager
    private lateinit var currentLatLng: LatLng;
    private var city = "南昌"
    private var currentSearchType= 1;
    override fun onGetIndoorRouteResult(p0: IndoorRouteResult?) {

    }

    override fun onGetTransitRouteResult(p0: TransitRouteResult?) {
    }

    override fun onGetDrivingRouteResult(result: DrivingRouteResult?) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(applicationContext, "抱歉，未找到结果", Toast.LENGTH_SHORT).show()
        }
        if (result!!.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            return
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            nodeIndex = -1


            if (false) {
                nowResultdrive = result
                if (!hasShownDialogue) {
                    val myTransitDlg = MyTransitDlg(activity as FragmentActivity, result.getRouteLines(), RouteLineAdapter.Type.DRIVING_ROUTE)
                    myTransitDlg.setOnDismissListener(DialogInterface.OnDismissListener { hasShownDialogue = false })
                    myTransitDlg.setOnItemInDlgClickLinster(object : OnItemInDlgClickListener {
                        override fun onItemClick(position: Int) {
                            route = nowResultdrive!!.getRouteLines().get(position)
                            val overlay = MyDrivingRouteOverlay(mBaiduMap)
                            mBaiduMap.setOnMarkerClickListener(overlay)
                            routeOverlay = overlay
                            overlay.setData(nowResultdrive!!.getRouteLines().get(position))
                            overlay.addToMap()
                            overlay.zoomToSpan()
                        }

                    })
                    myTransitDlg.show()
                    hasShownDialogue = true
                }
            } else if (result.getRouteLines().size == 1) {
                route = result.getRouteLines().get(0)
                val overlay = MyDrivingRouteOverlay(mBaiduMap)
                routeOverlay = overlay
                mBaiduMap.setOnMarkerClickListener(overlay)
                overlay.setData(result.getRouteLines().get(0))
                overlay.addToMap()
                overlay.zoomToSpan()

            } else {
                Log.d("route result", "结果数<0")
                return
            }

        }
    }

    override fun onGetWalkingRouteResult(p0: WalkingRouteResult?) {
    }

    override fun onGetMassTransitRouteResult(p0: MassTransitRouteResult?) {
    }

    override fun onGetBikingRouteResult(p0: BikingRouteResult?) {
    }

    override fun onGetGeoCodeResult(result: GeoCodeResult?) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(applicationContext, "抱歉，未能找到结果", Toast.LENGTH_LONG)
                    .show()
            return
        }
//        Log.e("TAG","${result.location.latitude}----------------${result.location.longitude}")
//        var strInfo = String.format("纬度：%f 经度：%f",
//        result.getLocation().latitude, result.getLocation().longitude);
//        Toast.makeText(applicationContext, strInfo, Toast.LENGTH_LONG).show();
        val enlat = LatLng(result.getLocation().latitude, result.getLocation().longitude)
//        val stlat = LatLng(28.681353, 115.909107)
//        mBaiduMap.clear()
//        mSearch!!.drivingSearch(DrivingRoutePlanOption()
//                .from(PlanNode.withLocation(currentLatLng)).to(PlanNode.withLocation(enlat)))
        when(currentSearchType){
            1 ->{
                val para = RouteParaOption()
                        .startPoint(currentLatLng)
                        .endPoint(enlat);
                BaiduMapRoutePlan.openBaiduMapDrivingRoute(para, applicationContext);
            }
            2->{
                val para = RouteParaOption()
                        .startPoint(currentLatLng)//路线检索起点
                        .endPoint(enlat);
                BaiduMapRoutePlan.openBaiduMapWalkingRoute(para,applicationContext);
            }
            3->{
                val para = RouteParaOption()
                        .startPoint(currentLatLng)
                        .endPoint(enlat)
                        .busStrategyType(EBusStrategyType.bus_recommend_way)
                BaiduMapRoutePlan.openBaiduMapTransitRoute(para, applicationContext);
            }
        }

    }

    override fun onGetReverseGeoCodeResult(p0: ReverseGeoCodeResult?) {

    }

    companion object {
        fun newInstance(isLazyLoad: Boolean): BaiDuFragment {
            val args = Bundle()
            args.putBoolean(LazyFragment.INTENT_BOOLEAN_LAZYLOAD, isLazyLoad)
            val fragment = BaiDuFragment()
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun onFragmentStartLazy() {

    }

    override fun onFragmentStopLazy() {
    }

    override fun onCreateViewLazy(savedInstanceState: Bundle?) {
        setContentView(R.layout.frag_baidu)
    }

    override fun onResumeLazy() {
        initclick();
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL
        mBaiduMap = mMapView.getMap()
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true)
        // 定位初始化
        mLocClient = LocationClient(applicationContext)
        mLocClient.registerLocationListener(MyLocationListenner())
        val option = LocationClientOption()
        option.isOpenGps = true // 打开gps
        option.setCoorType("bd09ll") // 设置坐标类型
        option.setScanSpan(1000)
        option.setIsNeedAddress(true)
        mLocClient.setLocOption(option)
        mLocClient.start()

    }

    override fun onDestroyViewLazy() {
        // 退出时销毁定位
        mLocClient.stop()
        // 关闭定位图层
        mBaiduMap.isMyLocationEnabled = false
        mMapView.onDestroy()

    }

    fun initclick() {
        // 初始化搜索模块，注册事件监听
        mSearch = RoutePlanSearch.newInstance()
        mGeoCoderSearch = GeoCoder.newInstance();
        mSearch!!.setOnGetRoutePlanResultListener(this)
        mGeoCoderSearch!!.setOnGetGeoCodeResultListener(this)
        search_driver.setOnClickListener {
            initSearch(baidu_frag_address.text.toString(),1)
        }

        search_walk.setOnClickListener {
            initSearch(baidu_frag_address.text.toString(),2)
        }
        search_bus.setOnClickListener {
            initSearch(baidu_frag_address.text.toString(),3)
        }
    }

    fun initSearch(text:String,type:Int){
        if (TextUtils.isEmpty(text)) {
            toast("地址不能为空")
        } else {
            currentSearchType =type;
            mGeoCoderSearch!!.geocode(GeoCodeOption().city(city).address(baidu_frag_address.getText().toString().trim()))
        }
    }

    /**
     * 定位SDK监听函数
     */
    inner class MyLocationListenner : BDLocationListener {

        override fun onReceiveLocation(location: BDLocation?) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return
            }
//            mCurrentLat = location.latitude
//            mCurrentLon = location.longitude
//            mCurrentAccracy = location.radius
            locData = MyLocationData.Builder()
                    .accuracy(location.radius)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection.toFloat()).latitude(location.latitude)
                    .longitude(location.longitude).build()
            mBaiduMap.setMyLocationData(locData)
            if (isFirstLoc) {
                isFirstLoc = false
                val ll = LatLng(location.latitude,
                        location.longitude)
                currentLatLng = LatLng(location.latitude,location.longitude);
                city = location.city
                val builder = MapStatus.Builder()
                builder.target(ll).zoom(18.0f)
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()))
            }
        }

        fun onReceivePoi(poiLocation: BDLocation) {}
    }

    // 定制RouteOverly
    inner class MyDrivingRouteOverlay(baiduMap: BaiduMap) : DrivingRouteOverlay(baiduMap) {
        override fun getStartMarker(): BitmapDescriptor {
            return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
        }
    }

    // 供路线选择的Dialog
    inner class MyTransitDlg(context: Context, transitRouteLines: List<RouteLine<*>>, type: RouteLineAdapter.Type) : Dialog(context, 0) {

        private lateinit var mtransitRouteLines: List<RouteLine<*>>
        private var transitRouteList: ListView? = null
        private lateinit var mTransitAdapter: RouteLineAdapter
        private lateinit var onItemInDlgClickListener: OnItemInDlgClickListener

        init {
            mtransitRouteLines = transitRouteLines
            mTransitAdapter = RouteLineAdapter(context, mtransitRouteLines, type)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
        }

        override fun setOnDismissListener(listener: DialogInterface.OnDismissListener?) {
            super.setOnDismissListener(listener)
        }

        override fun onCreate(savedInstanceState: Bundle) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_transit_dialog)

            transitRouteList = findViewById(R.id.transitList) as ListView
            transitRouteList!!.adapter = mTransitAdapter

            transitRouteList!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                onItemInDlgClickListener.onItemClick(position)
                dismiss()
                hasShownDialogue = false
            }
        }

        fun setOnItemInDlgClickLinster(itemListener: OnItemInDlgClickListener) {
            onItemInDlgClickListener = itemListener
        }

    }

    // 响应DLg中的List item 点击
    interface OnItemInDlgClickListener {
        fun onItemClick(position: Int)
    }


}