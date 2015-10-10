# LineChart
这是一个折线图的例子，包括了lib库（mylibrary）和demo(app)两个工程， 
这是一个简单的自定义折线图的练习，所以功能并不丰富

### 使用
##### 在布局中引入自定义控件
```
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools" android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">
    <com.example.like.mylibrary.LineChartView
        android:id="@+id/line_chart_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
</RelativeLayout>
```

##### 实现LineChartAdapter抽象类为LineChartView提供数据
```
adapter = new LineChartAdapter<Sale>(sales) {

            @Override
            protected String getIndex(Sale s) {
                return s.getDate();
            }

            @Override
            protected int getValue(Sale s) {
                return s.getSaleNum();
            }
        };
        mChartView.setAdapter(adapter);
```

sales提供数据， getIndex方法设置折线图下标，getValue方法获取折线图中的数值点

##### 可以重新设置数据，来更新LineChartView
```
adapter.setmDatas(sales);
adapter.notifyDataChanged();
```

##### 可自定的属性
```
<com.example.like.mylibrary.LineChartView
  android:id="@+id/line_chart_view"
  android:layout_width="match_parent"
  android:layout_height="match_parent"
  chart:backgroudColor="#00ff00"
  chart:divideLineColor="#0000ff"
  chart:chartAreaColor="#0ff000"
  chart:chartLineColor="#0fff00"
  chart:innerPadding="8dp"
  android:textColor="#ffffff"
  android:textSize="16sp"/>
```
可自定义的属性包括以下几个：
* backgroudColor 
* divideLineColor 
* chartAreaColor 
* chartLineColor 
* innerPadding 
* textColor 
* textSize 

