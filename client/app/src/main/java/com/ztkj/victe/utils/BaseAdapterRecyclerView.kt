package com.ztkj.victe.utils

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapterRecyclerView<T>(list: List<T>, itemLayoutId: Int):RecyclerView.Adapter<BaseAdapterRecyclerView.RecyclerHolder>(){
    private var list: MutableList<T>? = ArrayList<T>()//数据源
    private var itemLayoutId: Int = 0//
    private var recyclerView: RecyclerView? = null;
    private var listener: OnItemClickListener? = null;
    private var longClickListener: OnItemLongClickListener? = null;
    private val isScrolling: Boolean = false//是否在滚动
    init {
        this.list = list.toMutableList();
        this.itemLayoutId = itemLayoutId
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        var view: View = LayoutInflater.from(parent!!.context)!!.inflate(itemLayoutId, parent, false);
        return RecyclerHolder(view);
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        holder!!.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                Log.e("TAG","hello.....");
                if (listener != null && view != null && recyclerView != null) {
                    val position = recyclerView!!.getChildAdapterPosition(view)
                    listener!!.onItemClick(recyclerView!!, view, position);
                }
            }

        })
        holder!!.itemView.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(view: View?): Boolean {
                if (longClickListener != null && view != null && recyclerView != null) {
                    var position = recyclerView!!.getChildAdapterPosition(view);
                    longClickListener!!.onItemLongClick(recyclerView!!, view, position);
                    return true;
                }
                return false;
            }

        })
        convert(holder,list!!.get(position),position,isScrolling);
    }

    override fun getItemCount(): Int {
        return if (list == null) 0 else list!!.size
    }
    //添加item 局部刷新
    fun insert(item:T,position: Int){
        list!!.add(position,item);
        notifyItemInserted(position);
    }

    //删除item 局部刷新
    fun delete(position: Int){
        list!!.removeAt(position);
        notifyItemRemoved(position);
    }

    //修改item 局部刷新
    fun update(item:T,position: Int){
        list!!.removeAt(position)
        list!!.add(position,item);
        notifyItemChanged(position)
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener;
    }

    fun setOnItemLongClickListener(longlistener: OnItemLongClickListener) {
        this.longClickListener = longlistener;
    }
    public interface OnItemClickListener {
        fun onItemClick(parent: RecyclerView, view: View, position: Int);
    }

    public interface OnItemLongClickListener {
        fun onItemLongClick(parent: RecyclerView, view: View, position: Int): Boolean;
    }
    abstract fun convert(holder: RecyclerHolder, item: T, position: Int, isScrolling: Boolean)
    class RecyclerHolder(item:View):RecyclerView.ViewHolder(item)
}