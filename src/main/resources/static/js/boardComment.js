
console.log("boardComment.js in!!");
console.log(username);

// 수정, 삭제 이벤트
document.addEventListener('click', (e)=>{
    // 수정 버튼 - 1
    if(e.target.classList.contains("mod")){
        let cno = e.target.dataset.cno;
        let writer = e.target.dataset.writer;
        let content = e.target.dataset.content;
        // 모달 창에 데이터 전달
        document.getElementById('exampleModalLabel').innerText = writer;
        document.getElementById('cmtTextMod').value = content;
        document.getElementById('cmtModBtn').setAttribute("data-cno", cno);

    }
    // 삭제 버튼
    if(e.target.classList.contains("del")){
        let cno = e.target.dataset.cno;

        delCommToServer(cno).then(reault =>{
            if(reault == '1'){
                alert("댓글 삭제 성공!");
                printCommArea(bnoVal);
            }
        });
    }
});

// 수정 버튼 - 2
document.getElementById('cmtModBtn').addEventListener('click', ()=>{
    let cno = document.getElementById('cmtModBtn').dataset.cno;
    let writer = document.getElementById('exampleModalLabel').innerText;
    let content = document.getElementById('cmtTextMod').value;

    const cmtData = {
        cno : cno,
        writer : writer,
        content : content
    }

    modCommToServer(cmtData).then(result =>{
        if(result == '1'){
            alert("댓글 수정 성공!");
        }else{
            alert("댓글 수정 실패. 오류 발생!!");
        }
        document.querySelector('.btn-close').click();
        printCommArea(bnoVal);
    });
});

// 더보기 버튼
document.getElementById('moreBtn').addEventListener('click', ()=>{
    let page = parseInt(document.getElementById('moreBtn').dataset.page);
    printCommArea(bnoVal,page);
});

// 댓글 등록 버튼
document.getElementById('cmtAddBtn').addEventListener('click', ()=>{
    const writer = document.getElementById('cmtWriter');
    const content = document.getElementById('cmtText');

    if(username == 'anonymousUser'){
        alert("로그인 후 이용 가능합니다.");
        return false;
    }

    if(content.value == null || content.value == ""){
        alert("내용을 입력해주세요.");
        content.focus();
        return false;
    }


    const cmtData = {
        bno : bnoVal,
        writer : writer.innerText,
        content : content.value
    }

    console.log(cmtData);

    regCommToServer(cmtData).then(result =>{
        if(result == '1'){
            alert("댓글 등록 성공!");
            content.value = "";
            printCommArea(bnoVal);
        }else{
            alert("댓글 등록 실패. 오류 발생!!")
        }
    });

})

// 댓글 출력
function printCommArea(bno, page=1) {
    getCommFromServer(bno, page).then(result =>{
        const div = document.getElementById('cmtListArea');

        if(result.cmtList.length>0){
            if(page == 1){
                div.innerHTML = "";
            }

            for(cvo of result.cmtList){
                let str = `<div class="cmtBox"><p><strong>${cvo.writer}</strong> : ${cvo.content}</p>`;
                if(username == cvo.writer){
                    str += `<div class="btn-group me-2 btn-group-sm" role="group" aria-label="First group">`;
                    str += `<button type="button" class="btn btn-outline-secondary mod" data-cno=${cvo.cno} data-writer=${cvo.writer} data-content=${cvo.content} data-bs-toggle="modal" data-bs-target="#myModal">수정</button>`;
                    str += `<button type="button" class="btn btn-outline-secondary del" data-cno=${cvo.cno}>삭제</button></div>`;
                    str += `</div>`;
                }
                div.innerHTML += str;

            }

            let moreBtn = document.getElementById('moreBtn');

            if(result.pgvo.pageNo < result.realEndPage){
                moreBtn.style.visibility = 'visible'; 
                moreBtn.dataset.page = page + 1;  
            }else{
                moreBtn.style.visibility = 'hidden'; 
            }
            
        }
    });
}

// (비동기) 댓글 출력
async function getCommFromServer(bno, page) {
    try {
        const url = "/comment/list/" + bno + "/" + page;
        const resp = await fetch(url);
        const result = await resp.json();
        return result;
    } catch (error) {
        console.log(error);
    }
}

// (비동기) 댓글 작성
async function regCommToServer(cmtData) {
    try {
        const url = "/comment/register"
        const config = {
            method : 'post',
            headers : {
                'Content-Type' : 'application/json; charset=utf-8'
            },
            body : JSON.stringify(cmtData)
        };
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}

// (비동기) 댓글 삭제
async function delCommToServer(cno) {
    try {
        const url = "/comment/delete/" + cno;
        const config = {
            method : 'delete'
        };
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}

// (비동기) 댓글 수정
async function modCommToServer(cmtData) {
    try {
        const url = "/comment/modify";
        const config = {
            method : 'put',
            headers : {
                'Content-Type' : 'application/json; charset=utf-8'
            },
            body : JSON.stringify(cmtData)
        };
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}