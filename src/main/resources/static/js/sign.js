const inputValue = document.getElementById("exampleInputPassword4");
const sizeSecurity = document.getElementById("security");
const progressViewer = document.getElementsByClassName("progress")[0];

handleChange = (e) => {
    progressViewer.style.display = 'flex';
    const sizePassword = e.target.value.length;
    if (sizePassword === 0){
        progressViewer.style.display = 'none';
    }
    else if (sizePassword<7){
        sizeSecurity.style.width = '33%';
        sizeSecurity.style.backgroundColor = '#dc3545'; 
    }
    else if (sizePassword<11 && sizePassword>6){
        sizeSecurity.style.width = '66%';
        sizeSecurity.style.backgroundColor = '#ffc107';
    }
    else if (sizePassword>10){
        sizeSecurity.style.width = '100%';
        sizeSecurity.style.backgroundColor = '#198754';
    }
    

}

inputValue.addEventListener("input", handleChange);