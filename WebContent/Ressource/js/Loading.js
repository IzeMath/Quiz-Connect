$(window).on('load', function() {

	var pageWrap = document.getElementById( 'pagewrap' ),
	pages = [].slice.call( pageWrap.querySelectorAll( 'div.wrap' ) ),
	currentPage = 0,
	loader = new SVGLoader( document.getElementById( 'loader' ), { speedIn : 400, easingIn : mina.easeinout } );
			
	function init() {
		
		loader.show();
		setTimeout( function() {
		loader.hide();

		classie.removeClass( pages[ currentPage ], 'show' );
		// update..
		currentPage = currentPage ? 0 : 1;
		classie.addClass( pages[ currentPage ], 'show' );
		document.body.style.background="#17a2b8";
		}, 2000 );

}
	init();

})();

