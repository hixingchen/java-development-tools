"object" != typeof ue || "object" != typeof ue.interface ? ("object" != typeof ue && (ue = {}), ue.interface = {}, ue
	.interface.broadcast = function(e, t) {
		if ("string" == typeof e) {
			var o = [e, ""];
			void 0 !== t && (o[1] = t);
			var n = encodeURIComponent(JSON.stringify(o));
			"object" == typeof history && "function" == typeof history.pushState ? (history.pushState({}, "", "#" + n),
				history.pushState({}, "", "#" + encodeURIComponent("[]"))) : (document.location.hashs = n, document.location.hash =
				encodeURIComponent("[]"))
		}
	}) : function(e) {
	ue.interface = {}, ue.interface.broadcast = function(t, o) {
		"string" == typeof t && (void 0 !== o ? e.broadcast(t, JSON.stringify(o)) : e.broadcast(t, ""))
	}
}(ue.interface);
window.ue4 = ue.interface.broadcast;
