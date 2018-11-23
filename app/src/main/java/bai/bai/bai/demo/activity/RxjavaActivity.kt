package bai.bai.bai.demo.activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import bai.bai.bai.demo.R
import bai.bai.bai.demo.R.id.btn_rxjava
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.SingleOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rxjava.*

class RxjavaActivity : Activity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxjava)

        initListener()
    }

    private fun initListener() {
        btn_rxjava.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_rxjava -> {
                aboutSingle()
            }
        }
    }

    /**
     * 关于Single、just、subscribeOn、observeOn
     */
    private fun aboutSingle() {
        Single.just(5)//不管里面是什么，都会执行
                .observeOn(Schedulers.io())//--------------------从此以下都在io线程（除非有修改线程-----------------------------------）
                //传入的是int，传出的是转换后的String
                .flatMap {
                    Single.create(SingleOnSubscribe<String> { it ->
                        Log.d("baibai", "第一个flatMap所在线程 ： " + Thread.currentThread().name)//打印出来：RxCachedThreadScheduler-1
                        it.onSuccess(it.toString())

                    })
                }
                .observeOn(AndroidSchedulers.mainThread())//-----从此以下都在main线程（除非有修改线程-----------------------------------）
                //传入的是上面传进来的String，传出的是转换后的Int
                .flatMap {
                    Single.create(SingleOnSubscribe<Int> {
                        Log.d("baibai", "第二个flatMap所在线程 ： " + Thread.currentThread().name)//打印出来：main
                        it.onSuccess(2)
//                        it.onError(Exception("我是第二个终止的"))//执行完onError后下面的数据转换将不再实行
                    })
                }
                .observeOn(Schedulers.io())//--------------------从此以下都在io线程（除非有修改线程-----------------------------------）
                //传入的是上面传进来的Int，传出的是转换后的Boolean
                .flatMap {
                    val putin = it
                    Single.create(SingleOnSubscribe<Boolean> {
                        Log.d("baibai", "第三个flatMap所在线程 ： " + Thread.currentThread().name)//打印出来：RxCachedThreadScheduler-1
                        if (putin == 2) {
                            it.onSuccess(true)
                        }
                    })
                }
                //指定上游发送事件的线程，多次指定线程的话，只有第一次的起作用
                .subscribeOn(Schedulers.io())
                //指定下游接收事件的线程，多次指定线程的话，最后一次起作用
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<Boolean> {//这个尖括号就是上游传下来的数据类型

                    override fun onSuccess(t: Boolean) {
                        Log.d("baibai", "onSuccess -> $t")
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        Log.d("baibai", "onError -> $e ")
                    }

                })
    }


}
