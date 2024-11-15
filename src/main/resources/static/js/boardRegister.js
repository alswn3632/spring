
console.log("boardRegister.js in!!");

document.getElementById('trigger').addEventListener('click', ()=>{
    document.getElementById('file').click();
});

// 실행 파일 거부, 20MB 이상 파일 거부 
const regExp = new RegExp("\.(exe|jar|msi|dll|sh|bat)$");
const maxSize = 1024 * 1024 * 20;

function fileValidation(fileName, fileSize){
    if(regExp.test(fileName)){
        return 0;
    }else if(fileSize > maxSize){
        return 0;
    }else{
        return 1;
    }
}

document.addEventListener('change', (e)=>{
    if(e.target.id == 'file'){
        const fileObj = document.getElementById('file').files;
        console.log(fileObj);

        const fileZone = document.getElementById('fileZone');
        //초기화
        fileZone.innerHTML = "";
        let ul = `<ul class="list-group list-group-flush" style="width:500px;">`;
        let isOk = 1;
        for(let file of fileObj){
            let valid = fileValidation(file.name, file.size);
            isOk *= valid;
            ul += `<li class="list-group-item">`;
            ul += `<div style="width:100%; display:flex; justify-content: space-between;"><span>${file.name}</span>`;
            ul += `${valid? '<span class="badge text-bg-success"> 업로드 가능</span>' : '<span class="badge text-bg-danger"> 업로드 불가능</span>'}`;
            ul += `</div></li>`;

        }
        ul += '</ul>';
        fileZone.innerHTML = ul;

        if(isOk == 0){
            document.getElementById('regBtn').disabled = true;
        }

    }
});

/*
<ul class="list-group list-group-flush">
  <li class="list-group-item">An item</li>
</ul>
*/