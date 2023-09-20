/*=========================
	MyUtil.java
	- 게시판 페이징 처리
=========================*/

// check~!!!
// 이번에 같이 확인해보고자 하는 페이징 처리 기법은
// 다양한 방법들 중 한가지(그나마 쉬운 것을 골라...) 이다.
// 학습을 마친 이후에... 꼭~!!! 추가적으로 개념을 정리하고
// 확장을 시키고, 다른 방법들도 찾아보고 공부할 수 있도록 하자~!! 꼮~!!!
package com.util;

public class MyUtil
{
	// ■ 전체 페이지 수를 구하는 메소드
	// numPerPage : 한 페이지에 표시할 방명록의 수
	// dataCount : 전체 방명록 수
	public int getPageCount(int numPerPage, int dataCount)
	{
		int pageCount = 0;
		
		pageCount = dataCount / numPerPage;
		
		if (dataCount%numPerPage != 0)
			pageCount++;
		
		return pageCount;
	}
	
	
	// ■ 페이징 처리 기능의 메소드
	// currentPage : 현재 표시할 페이지
	// totalPage : 전체 페이지 수
	// listUrl : 링크를 설정할 url
	public String pageIndexList(int currentPage, int totalPage)
	{
		// 실제 페이징을 저장할 StringBuffer 변수
		StringBuffer strList = new StringBuffer();
		
		int numPerBlock = 10;
		//-- 페이징 처리 시 게시물 리스트 하단의 숫자를 10개씩 보여주겠다.
		
		int currentPageSetup;
		//-- 현재 페이지(이 페이지를 기준으로 보여주는 숫자가 달라져야 하기 때문...)
		
		int page;
		int n;
		//-- 이전 페이지 블럭과 같은 처리에서 이동하기 위한 변수
		//   (얼마만큼 이동해야 하는지...)
		
		// 페이징 처리가 별도로 필요하지 않은 경우
		//-- 데이터가 즉, 게시물이 존재하지 않아 페이징 처리를 할 필요가 없는 경우
		if (currentPage==0)
			return "";
		
		// ※ 페이지 요청을 처리하는 과정에서
		//    URL 경로의 패턴에 대한 처리
		/*
		      - 클라이언트 요청의 형태 → List.jsp
		         → (가공) → List.jsp + 『?』 + pageNum=1
		         → 『List.jsp?pageNum=1』 와 같은 형태
		         
		      - 클라이언트 요청의 형태 → List.jsp?searchKey=title
		         → (가공) → List.jsp?searchKey=title + 『&』 + pageNum=1
		         → 『List.jsp?searchKey=title&pageNum=1』 와 같은 형태
		*/
		
		
		// currentPageSetup = 표시할 리스트 페이지 중 첫 번째 페이지 - 1
		currentPageSetup = (currentPage / numPerBlock) * numPerBlock;
		//-- 만약 현재 페이지가 5페이지이고(currentPage=5)
		//   리스트 하단에 보여줄 페이지 갯수가 10 이면(numPerBlock=10)
		//   『5 / 10 = 0』 이며... 여기에 『* 10』 (10을 곱해도) 0 이다.
		//   하지만, 현재 페이지가 11 페이지라면(currentPage=11)
		//   『11 / 10 = 1』 이며... 여기에 『* 10』 (10을 곱하면) 10 이다.
		//   그러면... currentPageSetup 은 10이 되는 것이다.
		
		if (currentPage % numPerBlock == 0)
			currentPageSetup = currentPageSetup - numPerBlock;
		//-- 만약 위 처리에서... (라인 87)
		//   현재 페이지가 20 페이지였따면(currentPage=20)
		//   『20 / 2 = 2』 이며... 여기에 『* 10』 (10을 곱해서) 20이 되는데,
		//   이와 같은 상황이라면... 다시 10을 빼서 10으로 만들어주기 위한 구문.
		
		// 아래 if 문 조건 중 하나 생략 가능.
		
		// 1 페이지(맨처음으로)
		if ((totalPage>numPerBlock) && (currentPageSetup>0))
			strList.append(" <a href='javascript:page(1)' id='1'>1</a>");
		//-- listUrl 은 위에서 (라인 76 ~ 81) 이미 전처리가 끝난 상황이기 때문에
		//   『...?』 상태 또는 『...?...&』 인 상태이다.
		//   이로 인해 결과는
		//   『...?pageNume=1』 이거나 『...?...&pageNum=1』 이 되는 상황이다.
		
		n = currentPage - numPerBlock;
		
		if ((totalPage>numPerBlock) && (currentPageSetup>0))
			strList.append(" <a href='javascript:page("+n+")' id='"+n+"'>Prev</a>");
		//-- currentPageSetup 이 0 보다 큰 경우는
		//   이미 페이지가 11 페이지 이상이라는 의미이며
		//   이 때, 현재 페이지가(currentPage)가 11 이상일 경우
		//   『Prev』를 붙이기 위한 구문.
		//-- 『Prev』를 클릭할 경우
		//    n 변수 페이지로 이동하는데
		//    12 에서 『Prev』 할 경우 2 페이지가 되고,
		//    22 에서 『Prev』 할 경우 12 페이지가 된다.
		
		// 각 페이지 바로가기
		page = currentPageSetup + 1;
		//-- 『+1』을 수행하는 이유는
		//    앞에서 currentPageSetup 에서 10 을 가져왔다면
		//    10 부터 시작하는 것이 아니라
		//    바로가기 페이지는 11부터 시작해야 하기 때문이다.
		
		while ((page<=totalPage) && (page<=currentPageSetup+numPerBlock))
		{
			if (page==currentPage)
				// 현재 접속하고 있는 페이지는 클릭이 안되게 하기 위해서 a 태그가 아니라 span 태그로 설정
				strList.append(" <span style='color: orange; font-weight: bold;' m>"+page+"</span>");
			else
				strList.append(" <a href='javascript:page("+page+")'>"+page+"</a>");
			
			page++;
		}
		
		n = currentPage + numPerBlock;
		
		if ((totalPage-currentPageSetup) > numPerBlock)
			strList.append(" <a href='javascript:page("+n+")' id='"+n+"'>Next</a>");
		
		// 마지막 페이지(마지막으로)
		if ((totalPage>numPerBlock) && (currentPageSetup+numPerBlock)<totalPage)
			strList.append(" <a href='javascript:page("+totalPage+")' id='"+totalPage+"'>"+totalPage+"</a>");
		
		// 최종 페이징 처리된 내용 반환
		return strList.toString();
	}//end pageIndexList(int currentPage, int totalPage, String listUrl)
}
