package com.example.uncommonstore.faq

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uncommonstore.databinding.ActivityFaqBinding

class FaqActivity : AppCompatActivity() {
    // https://www.geeksforgeeks.org/how-to-create-expandable-recyclerview-items-in-android-using-kotlin/ 사이트 소스를 수정

    // view binding for the activity
    private var _binding: ActivityFaqBinding? = null
    private val binding get() = _binding!!

    // get reference to the adapter class
    private var languageList = ArrayList<DataItem>()
    private lateinit var expandableAdapter: ExpandableAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // define layout manager for the Recycler view
        binding.rvList.layoutManager = LinearLayoutManager(this)
        // attach adapter to the recyclerview
        expandableAdapter = ExpandableAdapter(languageList)

        getData()

        binding.rvList.adapter = expandableAdapter
    }

    private fun getData() {
        // 서버에서 가져온 데이터라고 가정한다.
        // create new objects and add some row data
        val language1 = DataItem(
                    "Q 언커먼스토어가 무엇인가요?",
            "더현대 서울 6층에 위치한 언커먼스토어는 비대면\n" +
                    "쇼핑이 즐비한 요즘, 직접 보고 고르는 쇼핑의 즐거움을\n" +
                    "잊지않도록 마련한 미래형 쇼핑공간입니다.\n" +
                    "\n" +
                    "언커먼스토어에서 새로운 경험을 해보세요.\n" +
                    "HAVE AN UNCOMMON TIME!\n",
            false
        )
        val language2 = DataItem(
            "Q 자동결제매장이 무엇인가요?",
            "오프라인 쇼핑의 필수 과정이었던 결제 단계가 매장\n" +
                    "안에서 이루어지는 것이 아니라, 고객님의 퇴장과 동시에\n" +
                    "자동으로 처리되는 매장입니다.\n" +
                    "\n" +
                    "앱을 이용해 매장에 입장하여 자유롭게 쇼핑하신 후,\n" +
                    "고른 상품을 들고 (별도의 계산 과정 없이)매장을\n" +
                    "나오시면 됩니다. 간편결제 시스템(현대식품관 to\n" +
                    "home의 H.Point.Pay)에 사전등록한 카드로 자동으로\n" +
                    "결제됩니다.\n",
            false
        )
        val language3 = DataItem(
            "Q 언커먼스토어 이용방법은 어떻게 되나요?",
            "언커먼스토어 매장 이용하시기 전 준비사항은 아래와\n" +
                    " 같습니다.\n" +
                    "\n" +
                    "1. 현대식품관 to home앱 회원가입 후 로그인\n " +
                    "해주세요.\n" +
                    "2. 언커먼스토어 자동결제 수단을 등록해주세요.\n" +
                    "H.Point Pay(현대식품관 to home 간편결제)내\n " +
                    "신용카드를 등록해 주시면 됩니다.\n" +
                    "-H.Point Pay 이용 중인 고객: 사전에 등록한 신용카드\n " +
                    "중 매장에서 사용할 자동결제수단을 선택/지정 해주세요.\n" +
                    "-H.Point Pay 미이용 고객 : [언커먼스토어>H.Point\n " +
                    "Pay 자동결제관리]에서 등록 가능합니다.\n\n" +
                    "3. 메인 메뉴 하단의 언커먼스토어 버튼을 클릭 하시면,\n" +
                    "언커먼스토어 매장 입장 가능한 QR코드가 생성됩니다.\n" +
                    "4. 매장 입장 전 발급받은 QR코드를 스피드게이트에\n" +
                    "스캔하면, 게이트가 열립니다.\n" +
                    "5. 매장 입장 후, 자유롭게 쇼핑해주세요.\n" +
                    "6. 쇼핑이 끝난 후 매장을 퇴장하시면, 고르신 상품이\n" +
                    "자동으로 결제 됩니다.",
            false
        )
        val language4 = DataItem(
                    "Q 언커먼스토어 영업시간/휴점일 확인은 어떻게\n " +
                    " 하나요?",
            "언커먼스토어는 더현대 서울 영업시간/휴점일과\n" +
                    "\n" +
                    "- 영업시간 : 주중 10시 30분 ~ 20: 00분, 주말 30분\n" +
                    "연장영업\n" +
                    "- 정기휴점 : 월별 상이\n",
            false
        )

        val language5 = DataItem(
            "Q 언커먼스토어 매장 입장하기가 안돼요.\n" +
                    "(입장 QR코드 발급실패)",
            "모바일 화면에 'QR코드 발급실패'가 뜨는 경우,\n" +
                    "언커먼스토어 직원에게 문의 바랍니다.\n" +
                    "\n" +
                    "※언커먼스토어(더현대 서울 담당 사무실) 02-3277-0731\n",
            false
        )

        val language6 = DataItem(
            "Q 언커먼스토어 쇼핑 중 유의사항은 무엇인가요?",
                "1. 구입의사가 없어진 상품은 꼭 제자리에 놓아주세요.\n" +
                        "2. 쇼핑 중 본인의 집은 상품을 다른 사람에게 건네주기\n" +
                        "마세요. 자동 결제가 될 수 있습니다.",
            false
        )

        val language7 = DataItem(
            "Q 1개의 QR 코드로 동반 입장이 가능한가요?",
            "네, 함께 입장 가능합니다. 일행 중 1분의 ID로\n" +
                    "로그인해주시고, 언커먼스토어 화면 하단의\n" +
                    "'언커먼스토어 입장하기'를 입장객 수 만큼 여러 번\n" +
                    "눌러서 입장해주세요. QR코드 한번 스캔 시, 한 분씩\n" +
                    "입장 가능합니다. 결제는 QR코드를 생성한 아이디에\n" +
                    "등록된 자동결제수단으로 진행됩니다.",
            false
        )

        val language8 = DataItem(
            "Q 현대식품관 투홈 비회원도 언커먼스토어를 이용할 수\n" +
                    "있나요?",
            "아니요. 언커먼스토어 매장 이용을 위해서는 아래의\n" +
                    "절차가 필요합니다.\n" +
                    "1. 현대식품관 to home 회원가입 후 로그인\n" +
                    "2. H.Point Pay 결제수단 신용카드를 등록 후\n" +
                    "언커먼스토어 자동결제 설정\n" +
                    "3. 미리 등록하신 6자리의 PIN번호를 입력하시면, 매장가능한 QR코드가 발급 됩니다.",
            false
        )

        val language9 = DataItem(
            "Q 회원(가입 및 탈퇴 등)관련 문의를 하고 싶습니다.",
            "회원(가입 및 탈퇴 등)과 관련된 문의사항은 현대식품관\n" +
                "자주 하는 질문 페이지에서 확인 가능합니다.\n" +
                "가입정보에 대한 자세한 사항은 현대식품관 투홈\n" +
            "상품과 관련된 문의사항은 언커먼스토어 직원에게 문의\n" +
                    "바랍니다. 언커먼스토어는 오프라인 매장으로, 상품에\n" +
                    "대한 온라인 구매는 어렵습니다.\n" +
                    "고객센터(1800-9549)로 문의 주시면 안내\n" +
                    "드리겠습니다.\n",
            false
        )

        val language10 = DataItem(
            "Q 상품 관련 문의는 어디에 하나요?",
                    "\n" +
                    "※언커먼스토어(더현대 서울 담당 사무실) 02-3277-0731",
            false
        )

        val language11 = DataItem(
            "Q 결제 내역 확인은 어떻게 하나요?",
            "[현대식품관 메인 화면 > 마이페이지 > 주문관리 >\n" +
                    "언커먼스토어 내역] 또는 [언커먼스토어 메인화면 >\n" +
                    "주문내역]에서 확인하실 수 있습니다.\n"
                    ,
            false
        )

        val language12 = DataItem(
            "Q 언커먼 스토어 매장 결재 내역에 오류가 있는 것\n" +
                    "같습니다.",
            "1. 언커먼스토어(더현대 서울 담당사무실)\n" +
                    "02-3277-0731 유선전화를 통한 문의\n" +
                    "\n" +
                    "2.[언커먼스토어 메인화면 > 주문내역 > 해당 주문의\n" +
                    "날씨(클릭) > 화면 하단의 결재금액(클릭)] '고객님,\n" +
                    "결제오류를 발견하셨나요? 내용칸에 문의사항 입력\n" +
                    "(7일 내 주문 건에 대해서만 확인 요청이 가능합니다.)",
            false
        )

        // add items to list
        languageList.add(language1)
        languageList.add(language2)
        languageList.add(language3)
        languageList.add(language4)
        languageList.add(language5)
        languageList.add(language6)
        languageList.add(language7)
        languageList.add(language8)
        languageList.add(language9)
        languageList.add(language10)
        languageList.add(language11)
        languageList.add(language12)

        expandableAdapter.notifyDataSetChanged()

    }

    // on destroy of view make the binding reference to null
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}