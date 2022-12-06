package com.example.uncommonstore.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.uncommonstore.event.db.EventDao
import com.example.uncommonstore.event.db.EventEntity
import com.example.uncommonstore.payment.db.CardDao
import com.example.uncommonstore.payment.db.CardEntity
import com.example.uncommonstore.product.db.ProductDao
import com.example.uncommonstore.product.db.ProductEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = arrayOf(ProductEntity::class, EventEntity::class, CardEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun ProductDao() : ProductDao
    //room Event 테이블 추가
    abstract fun EventDao() : EventDao

    // 2022.12.06 심지연 추가: room Card 테이블
    abstract fun CardDao() : CardDao

    companion object{
        val databaseName = "db"
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
    EventEntity(null, "망그러진 곰 X 더 현대 서울 언커먼 스토어", "https://user-images.githubusercontent.com/63277040/205828117-86c948c2-33c8-4d98-9e20-ad5dbe42ac11.PNG","행사기간 : 11/4(금)~12/22(목)"),
    EventEntity(null, "김미묘 X 언커먼스토어", "https://user-images.githubusercontent.com/63277040/205842977-6940e6a8-cd7f-4968-9900-de677cc35145.PNG","9/7(수) Open!"),
    EventEntity(null, "누누씨 X 언커먼스토어", "https://user-images.githubusercontent.com/63277040/205853086-460599d8-12dd-43dd-9e61-583c9d7dedfe.PNG","7월 15일 오픈!"),
    EventEntity(null, "여자아이들 팝업스토어", "https://user-images.githubusercontent.com/63277040/205853154-05d0813a-c54d-4c72-bd50-c3270ac3c143.PNG","여자아이들의 미니앨범 5집 I love 발매 기념 팝업스토어가 언커먼스토어에서 진행됩니다."),
    EventEntity(null, "PLAY HEENDY DAY !", "https://user-images.githubusercontent.com/63277040/205853217-129fdc0a-5a8f-441b-b05e-6ca2cc504465.PNG","흰디 굿즈를 특별한 가격에 만나보세요!\n"),
    EventEntity(null, "김토끼 스튜도 X 언커먼스토어", "https://user-images.githubusercontent.com/63277040/205853317-5f420c49-1ceb-4655-af7a-f9b7ab5ed61f.PNG","기간 : 6/3 ~ 7/7"),
    EventEntity(null, "바리수 X 언커먼스토어", "https://user-images.githubusercontent.com/63277040/205853382-f22d236f-267a-4cc8-9a83-112ac0b3fba9.PNG","기간 : 4/22(금) ~ 6/2(목)"),
    EventEntity(null, "바잇미 팝업스토어", "https://user-images.githubusercontent.com/63277040/205853444-1ff5348d-18e0-4b2e-a12f-c14f1e461927.PNG","일정 : 3/12(토) ~ 4/21(목)")
    )