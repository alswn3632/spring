
console.log("boardDetail.js in!!");

document.getElementById('listBtn').addEventListener('click', ()=>{
    // 목록으로 이동
    location.href="/board/list";
});

document.getElementById('modBtn').addEventListener('click', ()=>{
    // 수정버튼 클릭 >> 수정상태로 변환!

    // 각 input 태그 readOnly 해제
    document.getElementById('title').readOnly = false;
    document.getElementById('writer').readOnly = false;
    document.getElementById('content').readOnly = false;

    document.getElementById('h3').innerText = "Board Modify Page!!";

    // modBtn, delBtn 삭제
    document.getElementById('modBtn').remove();
    document.getElementById('delBtn').remove();

    // submit 타입의 저장 버튼 추가
    let modBtn = document.createElement('button'); //<button></button>
    modBtn.setAttribute('type', 'submit') //<button type="submit"></button>
    modBtn.classList.add('btn', 'btn-secondary', 'btn-sm');
    modBtn.innerText = "저장";

    document.getElementById('modForm').appendChild(modBtn);
});

document.getElementById('delBtn').addEventListener('click', ()=>{
    let ok = confirm("삭제하시겠습니까?");
    if(ok === true){
        const bno = document.getElementById('bno').value; 
        location.href="/board/delete?bno=" + bno;
    }
});
