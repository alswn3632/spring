
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
    modBtn.setAttribute('type', 'submit'); //<button type="submit"></button>
    modBtn.setAttribute('id', 'regBtn');
    modBtn.classList.add('btn', 'btn-secondary', 'btn-sm');
    modBtn.innerText = "저장";

    document.getElementById('modForm').appendChild(modBtn);

    let fileDelBtn = document.querySelectorAll(".file-x");
    for(let delBtn of fileDelBtn){
        delBtn.disabled = false;
    }

    document.querySelector(".file-up").disabled = false;

});

document.getElementById('delBtn').addEventListener('click', ()=>{
    let ok = confirm("삭제하시겠습니까?");
    if(ok === true){
        const bno = document.getElementById('bno').value; 
        location.href="/board/delete?bno=" + bno;
    }
});

document.addEventListener('click', (e)=>{
    if(e.target.classList.contains('file-x')){
        let uuid = e.target.dataset.uuid
        deleteFileToServer(uuid).then(result  =>{
            if(result == '1'){
                console.log("파일 삭제 성공");
                e.target.closest('div').remove();

            }else{
                alert("삭제를 진행할 수 없습니다.");
            }
        });
    }
});

async function deleteFileToServer(uuid) {
    try {
        const url = "/board/file/" + uuid;
        const config = {
            method : 'delete'
        };
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error)
    }
}