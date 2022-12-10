package com.example.uncommonstore.payment.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/*****************************************************
 * @function : CardDao
 * @author : 심지연
 * @Date : 2022.12.05 생성
 *****************************************************/

@Dao
interface CardDao {
    // 카드 입력폼에서 데이터를 입력하면 카드 정보가 삽입되도록 하는 쿼리
    @Insert
    fun insertCard(card: CardEntity)

    // 카드 이름을 얻어오는 쿼리
    @Query("SELECT cardName FROM card")
    fun getCardName(): String

    // 카드 리스트를 얻어오는 쿼리
    @Query("SELECT * FROM card")
    fun getCardList(): List<CardEntity>

    // 다이얼로그에서 선택한 이름의 특정 카드만 삭제하기 위한 쿼리
    @Query("DELETE FROM card WHERE cardName = :cardName")
    fun deleteCard(cardName: String?)
}