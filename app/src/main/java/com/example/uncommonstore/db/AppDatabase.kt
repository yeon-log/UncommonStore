package com.example.uncommonstore.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.uncommonstore.product.db.ProductDao
import com.example.uncommonstore.product.db.ProductEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = arrayOf(ProductEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun ProductDao() : ProductDao

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