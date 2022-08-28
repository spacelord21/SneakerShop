const priority = document.getElementsByClassName("priority");
const button = document.getElementById("click");
const form = document.getElementById("form");


for(let i = 0; i < priority.length;i++) {
    if(priority.item(i).attributes.item(2).value==='PRIORITY') {
        priority.item(i).style.color = 'red';
    } else if(priority.item(i).attributes.item(2).value==='SECONDARY') {
        priority.item(i).style.color = 'yellow';
    } else if(priority.item(i).attributes.item(2).value==='UNIMPORTANT'){
        priority.item(i).style.color = 'blue';
    }
}



handleClick = () => {
    form.style.display = 'block';
    button.style.display = 'none';
}

closeForm = () => {
    form.style.display = 'none';
    button.style.display = 'block';
}


