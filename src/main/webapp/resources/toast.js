const Toast = Swal.mixin({
	  toast: true,
	  position: 'top-end',
	  showConfirmButton: false,
	  width: 270,
	  timer: 4000,	  
	  timerProgressBar: true,
	  onOpen: (toast) => {
	    toast.addEventListener('mouseenter', Swal.stopTimer)
	    toast.addEventListener('mouseleave', Swal.resumeTimer)
	  }
});


// https://sweetalert2.github.io/#examples
// ICONS: success error warning info question

function fireToast( icon, title, text ){
	
	Toast.fire({
		title: title,
		text: text,
		icon: icon,
		footer: '<a href="#">Precisa de mais informação?</a>',
	});

}
