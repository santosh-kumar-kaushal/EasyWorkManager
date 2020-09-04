[![](https://jitpack.io/v/santosh-kumar-kaushal/EasyWorkManager.svg)](https://jitpack.io/#santosh-kumar-kaushal/EasyWorkManager)

# EasyWorkManager

- Easy to use workmanager library.

# Overview of workmanager library

- Schedule one time task.
- Schedule periodic time task.
- Schedule custom task with constraints.


## Using workmanager Library in your Android application

#### Add this in your build.gradle - Project level

```allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}
```

#### Add this in your build.gradle - App level

``` 
implementation 'com.github.santosh-kumar-kaushal:EasyWorkManager:1.0'

```     

#### Then initialize it in onCreate() Method of Activity/Fragment/any class

```
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
       //Build Task scheduler with different options.
        TaskScheduler.Builder(this,this).
        setListener(this).setWorkType(WorkType.PERIODIC)
            .setPeriodicTime(min.toLong()).build()
        }
```

#### Implement ITaskExecutionCallback to start a background task and receive callback on task status.

 ```
    override fun onTaskExecutionInProgress() {
        Log.e("MainActivity","onTaskExecutionInProgress...")
    }

    override fun onTaskExecutionCompleted() {
        Log.e("MainActivity","onTaskExecutionCompleted.")
    }

    override fun onTaskExecutionFailed() {
        Log.e("MainActivity","onTaskExecutionFailed.")
    }

    override fun startBackgroundTask() {
        Log.e("MainActivity","startBackgroundTask.")
    }
 ```
 
 #### This is how simple your activity will look like.
 
 ```
 class MainActivity : AppCompatActivity(),ITaskExecutionCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val min=20*60000
        setContentView(R.layout.activity_main)

        //Build Task scheduler with different options.
        TaskScheduler.Builder(this,this).
        setListener(this).setWorkType(WorkType.PERIODIC)
            .setPeriodicTime(min.toLong()).build()
    }

    override fun onTaskExecutionInProgress() {
        Log.e("MainActivity","onTaskExecutionInProgress...")
    }

    override fun onTaskExecutionCompleted() {
        Log.e("MainActivity","onTaskExecutionCompleted.")
    }

    override fun onTaskExecutionFailed() {
        Log.e("MainActivity","onTaskExecutionFailed.")
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun startBackgroundTask() {
        Log.e("MainActivity","startBackgroundTask.")
        fetchDataFromApi()
    }
 ```

#### Want to create your own task scheduler just extend TaskScheduler.

```
class OwnTaskScheduler(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    iTaskExecutionCallback: ITaskExecutionCallback,
    workType: WorkType
) : TaskScheduler(context, lifecycleOwner, iTaskExecutionCallback, workType) {

    private lateinit var oneTimeWorkRequest: OneTimeWorkRequest

    //Your own constraints.
    override var constraints: Constraints =
        Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

    //Your own request type.
    override fun scheduleCustomRequest() {
        oneTimeWorkRequest =
            OneTimeWorkRequest.Builder(BackGroundWorker::class.java)
                .setConstraints(constraints)
                .build()
        workManager.enqueue(oneTimeWorkRequest)
        //Set the callbacks to listen to changes.
        listenToCallbacks(oneTimeWorkRequest.id)
    }
}
```

# License

```
   Copyright (C) 2020 Santosh Kumar

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
   
  ```
