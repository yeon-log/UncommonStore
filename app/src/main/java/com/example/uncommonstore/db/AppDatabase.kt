package com.example.uncommonstore.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.uncommonstore.event.db.EventDao
import com.example.uncommonstore.event.db.EventEntity
import com.example.uncommonstore.product.db.ProductDao
import com.example.uncommonstore.product.db.ProductEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = arrayOf(ProductEntity::class, EventEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun ProductDao() : ProductDao
    //room Event 테이블 추가
    abstract fun EventDao() : EventDao

    companion object{
        val databaseName = "product"
        var appDatabase : AppDatabase? = null
        fun getInstance(context: Context):AppDatabase?{
            /*if(appDatabase == null){
                appDatabase = Room.databaseBuilder(context,
                AppDatabase::class.java,
                databaseName).build()
            }*/

            if(appDatabase == null){
                synchronized(AppDatabase::class){
                    //데이터베이스 인스턴스를 생성하고 해당 인스턴스로 DAO인스턴스의 메서드를 사용하여 데이터베이스와 상호작용
                    appDatabase = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        databaseName
                    ).addCallback(object : RoomDatabase.Callback(){
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            fillInDb(context.applicationContext)
                            //room event 데이터 추가(결제수단은 추가 안해도됨)
                            fillInEventDb(context.applicationContext)
                        }
                    }).build()
                }
            }

            return appDatabase
        }
        fun fillInDb(context: Context){
            CoroutineScope(Dispatchers.IO).launch {
                getInstance(context)!!.ProductDao().insertProduct(
                    PRODUCT_DATA
                )
            }
        }

        // room 이벤트 기본 데이터 추가 함수
        fun fillInEventDb(context: Context){
            CoroutineScope(Dispatchers.IO).launch {
                getInstance(context)!!.EventDao().insertEvent(
                    EVENT_DATA
                )
            }
        }
    }
}
private val PRODUCT_DATA = arrayListOf(
    ProductEntity(null, "꿀잠친구_베어춘식이", "39,000",0, "https://user-images.githubusercontent.com/70796139/205591848-e9160e4a-0f1d-48a4-a845-2be8e76d183f.png","상품 내용"),
    ProductEntity(null, "윈터어드벤처 눈집게라이언", "39,000",0, "https://user-images.githubusercontent.com/70796139/205591856-614e0193-d788-4402-a182-f5a74782a767.png","상품 내용"),
    ProductEntity(null, "안고자는 매직바디 필로우_라이언", "39,000",0, "https://user-images.githubusercontent.com/70796139/205592371-78095e21-3e9a-49c8-af50-2ea2f71b983b.png","상품 내용"),
    ProductEntity(null, "혀딻은앙꼬 슬리퍼", "39,000",0, "https://user-images.githubusercontent.com/70796139/205592174-360902a3-bc99-4d08-827b-15d12fb54415.png","상품 내용"),
    ProductEntity(null, "꿀잠친구_베어춘식이", "39,000",0, "https://user-images.githubusercontent.com/70796139/205591848-e9160e4a-0f1d-48a4-a845-2be8e76d183f.png","상품 내용"),
    ProductEntity(null, "윈터어드벤처 눈집게라이언", "39,000",0, "https://user-images.githubusercontent.com/70796139/205591856-614e0193-d788-4402-a182-f5a74782a767.png","상품 내용"),
    ProductEntity(null, "안고자는 매직바디 필로우_라이언", "39,000",0, "https://user-images.githubusercontent.com/70796139/205592371-78095e21-3e9a-49c8-af50-2ea2f71b983b.png","상품 내용"),
    ProductEntity(null, "혀딻은앙꼬 슬리퍼", "39,000",0, "https://user-images.githubusercontent.com/70796139/205592174-360902a3-bc99-4d08-827b-15d12fb54415.png","상품 내용"),
    ProductEntity(null, "꿀잠친구_베어춘식이", "39,000",0, "https://user-images.githubusercontent.com/70796139/205591848-e9160e4a-0f1d-48a4-a845-2be8e76d183f.png","상품 내용"),
    ProductEntity(null, "윈터어드벤처 눈집게라이언", "39,000",0, "https://user-images.githubusercontent.com/70796139/205591856-614e0193-d788-4402-a182-f5a74782a767.png","상품 내용"),
    ProductEntity(null, "안고자는 매직바디 필로우_라이언", "39,000",0, "https://user-images.githubusercontent.com/70796139/205592371-78095e21-3e9a-49c8-af50-2ea2f71b983b.png","상품 내용"),
    ProductEntity(null, "혀딻은앙꼬 슬리퍼", "39,000",0, "https://user-images.githubusercontent.com/70796139/205592174-360902a3-bc99-4d08-827b-15d12fb54415.png","상품 내용"),
    )

//room 기본 데이터 세팅
private val EVENT_DATA = arrayListOf(
    EventEntity(null, "꿀잠친구_베어춘식이", "https://user-images.githubusercontent.com/63277040/205828117-86c948c2-33c8-4d98-9e20-ad5dbe42ac11.PNG","상품 내용"),
)