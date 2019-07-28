package com.example.test;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    SeekBar sb;
    TextView tv;
    Button btn1,btn2,btn3;

    private static String TAG="Salman";
    CompositeDisposable disposable=new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // Toolbar toolbar = findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);

    /*    FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        sb=(SeekBar)findViewById(R.id.seekbar);
        tv=(TextView)findViewById(R.id.text2);
        btn1=(Button) findViewById(R.id.btn1);
        btn2=(Button) findViewById(R.id.btn2);
        btn3=(Button) findViewById(R.id.btn3);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Eample1();
             //   Example2();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Operators();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i =new Intent(getApplicationContext(),MainActivity2.class);
                startActivity(i);
                finish();
            }
        });

    }


    private void Operators() {

        CreateOperator();
        JustOperator();
        RangeOperator();
        RepeateOperator();

    }

    private void RangeOperator() {

        //SIMPLE RANGE OPERATOR
        Observable<Integer> observable=Observable
                .range(0,10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

              observable.subscribe(new Observer<Integer>() {
                  @Override
                  public void onSubscribe(Disposable d) {

                  }

                  @Override
                  public void onNext(Integer integer) {

                      Log.d(TAG,"On Next"+integer);
                  }

                  @Override
                  public void onError(Throwable e) {

                  }

                  @Override
                  public void onComplete() {

                  }
              });

              //WITH MAP

        Observable<Task> observable1=Observable
                .range(10,20)
                .subscribeOn(Schedulers.io())
                .map(new Function<Integer, Task>() {
                    @Override
                    public Task apply(Integer integer) throws Exception {
                        return new Task("i have a dog"+String.valueOf(integer),true,integer);
                    }
                })
                .takeWhile(new Predicate<Task>() {
                    @Override
                    public boolean test(Task task) throws Exception {
                        return task.getPriority()<20;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());

        observable1.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Task task) {

                Log.d(TAG,"On NExt"+task.getPriority());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    private void RepeateOperator() {

        Observable<Integer> integerObservable=Observable
                .range(0,3)
                .subscribeOn(Schedulers.io())
                .repeat(2)//repeats 2 times
                .observeOn(AndroidSchedulers.mainThread());

        integerObservable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
             Log.d(TAG,"On next"+integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void JustOperator() {
        final Task task=new Task("walk the dog",false,3);

            Task t=Task.getInstance();
            Task t1=Task.getInstance();

        //for single Object
        Observable<Task> taskObservable=Observable
                .just(task,task)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread());

        taskObservable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Task task) {
                Log.d(TAG, "On  Next Called: "+task.getDesc());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        //for list of Objects
        Observable<Task> taskObservable1=Observable
                .create(new ObservableOnSubscribe<Task>() {
                    @Override
                    public void subscribe(ObservableEmitter<Task> emitter) throws Exception {

                        for(Task task1:Datasource.CreatList())
                        {
                            if(!emitter.isDisposed())
                            {
                                emitter.onNext(task1);
                            }
                        }

                        if(!emitter.isDisposed())
                        {
                            emitter.onComplete();
                        }
                    }
                })
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread());

        taskObservable1.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Task task) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void CreateOperator() {
        final Task task=new Task("walk the dog",false,3);

        //for single Object
        Observable<Task> taskObservable=Observable
                .create(new ObservableOnSubscribe<Task>() {
                    @Override
                    public void subscribe(ObservableEmitter<Task> emitter) throws Exception {

                        if(!emitter.isDisposed())
                        {
                            emitter.onNext(task);
                            emitter.onComplete();
                        }
                    }
                })
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread());

                taskObservable.subscribe(new Observer<Task>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Task task) {
                        Log.d(TAG, "On  Next Called: "+task.getDesc());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

                //for list of Objects
        Observable<Task> taskObservable1=Observable
                .create(new ObservableOnSubscribe<Task>() {
                    @Override
                    public void subscribe(ObservableEmitter<Task> emitter) throws Exception {

                        for(Task task1:Datasource.CreatList())
                        {
                            if(!emitter.isDisposed())
                            {
                                emitter.onNext(task1);
                            }
                        }

                        if(!emitter.isDisposed())
                        {
                            emitter.onComplete();
                        }
                    }
                })
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread());

        taskObservable1.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Task task) {
                Log.d(TAG, "On  Next Called: "+task.getDesc());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void Example2() {
        // observable
        Observable<String> animalsObservable = getAnimalsObservable();

        // observer
        Observer<String> animalsObserver = getAnimalsObserver();

        // observer subscribing to observable
        animalsObservable
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(animalsObserver);
    }

    private void Eample1() {

        final Boolean[] isenable = new Boolean[1];

        Observable<Task> taskObservable = Observable
                .fromIterable(Datasource.CreatList())

                .filter(new Predicate<Task>() {
                    @Override
                    public boolean test(Task task) throws Exception {
                        Log.d(TAG, "test: "+Thread.currentThread().getName());

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                        return task.isComplete();
                    }
                })
                .observeOn(Schedulers.io()).subscribeOn(Schedulers.io());

        taskObservable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(Disposable d) {

                disposable.add(d);
                Log.d(TAG, "On Subscribe Called");
                for(int i=0;i<10;i++)
                {
                    Log.d(TAG, "On Subscribe Called "+i);
                    if(i==9)
                    {
                        isenable[0] =false;
                    }
                }

                btn2.setEnabled(false);
            }

            @Override
            public void onNext(Task task) {
                if(isenable[0] )
                btn2.setEnabled(true);
                Log.d(TAG, "On Next Called");
                Log.d(TAG, "On  Next Called: "+task.getDesc());
                Log.d(TAG, "On Next Called"+Thread.currentThread().getName());

            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "On Error Called");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "On Complte Called");
            }
        });

    }

    private Observer<String> getAnimalsObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "Name: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "All items are emitted!");
            }
        };


    }

    private Observable<String> getAnimalsObservable() {
        return Observable.just("Ant", "Bee", "Cat", "Dog", "Fox");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
