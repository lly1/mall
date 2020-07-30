﻿var ScaleWidth=0;//裁剪后图片宽度
var Scale=1;//图片缩放倍数
(function($) {


	if(jQuery.browser) return; 

	jQuery.browser = {}; 
	jQuery.browser.mozilla = false; 
	jQuery.browser.webkit = false; 
	jQuery.browser.opera = false; 
	jQuery.browser.msie = false; 

	var nAgt = navigator.userAgent; 
	jQuery.browser.name = navigator.appName; 
	jQuery.browser.fullVersion = ''+parseFloat(navigator.appVersion); 
	jQuery.browser.majorVersion = parseInt(navigator.appVersion,10); 
	var nameOffset,verOffset,ix; 

	// In Opera, the true version is after "Opera" or after "Version" 
	if ((verOffset=nAgt.indexOf("Opera"))!=-1) { 
	jQuery.browser.opera = true; 
	jQuery.browser.name = "Opera"; 
	jQuery.browser.fullVersion = nAgt.substring(verOffset+6); 
	if ((verOffset=nAgt.indexOf("Version"))!=-1) 
	jQuery.browser.fullVersion = nAgt.substring(verOffset+8); 
	} 
	// In MSIE, the true version is after "MSIE" in userAgent 
	else if ((verOffset=nAgt.indexOf("MSIE"))!=-1) { 
	jQuery.browser.msie = true; 
	jQuery.browser.name = "Microsoft Internet Explorer"; 
	jQuery.browser.fullVersion = nAgt.substring(verOffset+5); 
	} 
	// In Chrome, the true version is after "Chrome" 
	else if ((verOffset=nAgt.indexOf("Chrome"))!=-1) { 
	jQuery.browser.webkit = true; 
	jQuery.browser.name = "Chrome"; 
	jQuery.browser.fullVersion = nAgt.substring(verOffset+7); 
	} 
	// In Safari, the true version is after "Safari" or after "Version" 
	else if ((verOffset=nAgt.indexOf("Safari"))!=-1) { 
	jQuery.browser.webkit = true; 
	jQuery.browser.name = "Safari"; 
	jQuery.browser.fullVersion = nAgt.substring(verOffset+7); 
	if ((verOffset=nAgt.indexOf("Version"))!=-1) 
	jQuery.browser.fullVersion = nAgt.substring(verOffset+8); 
	} 
	// In Firefox, the true version is after "Firefox" 
	else if ((verOffset=nAgt.indexOf("Firefox"))!=-1) { 
	jQuery.browser.mozilla = true; 
	jQuery.browser.name = "Firefox"; 
	jQuery.browser.fullVersion = nAgt.substring(verOffset+8); 
	} 
	// In most other browsers, "name/version" is at the end of userAgent 
	else if ( (nameOffset=nAgt.lastIndexOf(' ')+1) < 
	(verOffset=nAgt.lastIndexOf('/')) ) 
	{ 
	jQuery.browser.name = nAgt.substring(nameOffset,verOffset); 
	jQuery.browser.fullVersion = nAgt.substring(verOffset+1); 
	if (jQuery.browser.name.toLowerCase()==jQuery.browser.name.toUpperCase()) { 
	jQuery.browser.name = navigator.appName; 
	} 
	} 
	// trim the fullVersion string at semicolon/space if present 
	if ((ix=jQuery.browser.fullVersion.indexOf(";"))!=-1) 
	jQuery.browser.fullVersion=jQuery.browser.fullVersion.substring(0,ix); 
	if ((ix=jQuery.browser.fullVersion.indexOf(" "))!=-1) 
	jQuery.browser.fullVersion=jQuery.browser.fullVersion.substring(0,ix); 

	jQuery.browser.majorVersion = parseInt(''+jQuery.browser.fullVersion,10); 
	if (isNaN(jQuery.browser.majorVersion)) { 
	jQuery.browser.fullVersion = ''+parseFloat(navigator.appVersion); 
	jQuery.browser.majorVersion = parseInt(navigator.appVersion,10); 
	} 
	jQuery.browser.version = jQuery.browser.majorVersion; 
	$.Jcrop = function(obj, opt) {
		var obj = obj,
		opt = opt;
		if (typeof(obj) !== "object") {
			obj = $(obj)[0]
		}
		if (typeof(opt) !== "object") {
			opt = {}
		}
		if (! ("trackDocument" in opt)) {
			opt.trackDocument = $.browser.msie ? false: true;
			if ($.browser.msie && $.browser.version.split(".")[0] == "8") {
				opt.trackDocument = true
			}
		}
		if (! ("keySupport" in opt)) {
			opt.keySupport = $.browser.msie ? false: true
		}
		var defaults = {
			trackDocument: false,
			baseClass: "jcrop",
			addClass: null,
			bgColor: "black",
			bgOpacity: 0.6,
			borderOpacity: 0.4,
			handleOpacity: 0.5,
			handlePad: 5,
			handleSize: 9,
			handleOffset: 5,
			edgeMargin: 14,
			aspectRatio: 0,
			keySupport: true,
			cornerHandles: true,
			sideHandles: true,
			drawBorders: true,
			dragEdges: true,
			boxWidth: 0,
			boxHeight: 0,
			boundary: 8,
			animationDelay: 20,
			swingSpeed: 3,
			allowSelect: true,
			allowMove: true,
			allowResize: true,
			minSelect: [0, 0],
			maxSize: [0, 0],
			minSize: [0, 0],			
			onChange: function() {},
			onSelect: function() {}
		};
		var options = defaults;
		setOptions(opt);
		var $origimg = $(obj);
		var $img = $origimg.clone().removeAttr("id").css({
			position: "absolute"
		});
		$img.width($origimg.width());
		$img.height($origimg.height());
		$origimg.after($img).hide();
		presize($img, options.boxWidth, options.boxHeight);
		var boundx = $img.width(),
		boundy = $img.height(),
		$div = $("<div />").width(boundx).height(boundy).addClass(cssClass("holder")).css({
			position: "relative",
			backgroundColor: options.bgColor
		}).insertAfter($origimg).append($img);
		if (options.addClass) {
			$div.addClass(options.addClass)
		}
		var $img2 = $("<img />").attr("src", $img.attr("src")).css("position", "absolute").width(boundx).height(boundy);
		var $img_holder = $("<div />").width(pct(100)).height(pct(100)).css({
			zIndex: 310,
			position: "absolute",
			overflow: "hidden"
		}).append($img2);
		var $hdl_holder = $("<div />").width(pct(100)).height(pct(100)).css("zIndex", 320);
		var $sel = $("<div />").css({
			position: "absolute",
			zIndex: 300
		}).insertBefore($img).append($img_holder, $hdl_holder);
		var bound = options.boundary;
		var $trk = newTracker().width(boundx + (bound * 2)).height(boundy + (bound * 2)).css({
			position: "absolute",
			top: px( - bound),
			left: px( - bound),
			zIndex: 290
		}).mousedown(newSelection);
		var xlimit, ylimit, xmin, ymin;
		var xscale, yscale, enabled = true;
		var docOffset = getPos($img),
		btndown,
		lastcurs,
		dimmed,
		animating,
		shift_down;
		var Coords = function() {
			var x1 = 0,
			y1 = 0,
			x2 = 0,
			y2 = 0,
			ox, oy;
			function setPressed(pos) {
				var pos = rebound(pos);
				x2 = x1 = pos[0];
				y2 = y1 = pos[1]
			}
			function setCurrent(pos) {
				var pos = rebound(pos);
				ox = pos[0] - x2;
				oy = pos[1] - y2;
				x2 = pos[0];
				y2 = pos[1]
			}
			function getOffset() {
				return [ox, oy]
			}
			function moveOffset(offset) {
				var ox = offset[0],
				oy = offset[1];
				if (0 > x1 + ox) {
					ox -= ox + x1
				}
				if (0 > y1 + oy) {
					oy -= oy + y1
				}
				if (boundy < y2 + oy) {
					oy += boundy - (y2 + oy)
				}
				if (boundx < x2 + ox) {
					ox += boundx - (x2 + ox)
				}
				x1 += ox;
				x2 += ox;
				y1 += oy;
				y2 += oy
			}
			function getCorner(ord) {
				var c = getFixed();
				switch (ord) {
				case "ne":
					return [c.x2, c.y];
				case "nw":
					return [c.x, c.y];
				case "se":
					return [c.x2, c.y2];
				case "sw":
					return [c.x, c.y2]
				}
			}
			function getFixed() {
				if (!options.aspectRatio) {
					return getRect()
				}
				var aspect = options.aspectRatio,
				min_x = options.minSize[0] / xscale,
				min_y = options.minSize[1] / yscale,
				max_x = options.maxSize[0] / xscale,
				max_y = options.maxSize[1] / yscale,
				rw = x2 - x1,
				rh = y2 - y1,
				rwa = Math.abs(rw),
				rha = Math.abs(rh),
				real_ratio = rwa / rha,
				xx,
				yy;
				if (max_x == 0) {
					max_x = boundx * 10
				}
				if (max_y == 0) {
					max_y = boundy * 10
				}
				if (real_ratio < aspect) {
					yy = y2;
					w = rha * aspect;
					xx = rw < 0 ? x1 - w: w + x1;
					if (xx < 0) {
						xx = 0;
						h = Math.abs((xx - x1) / aspect);
						yy = rh < 0 ? y1 - h: h + y1
					} else {
						if (xx > boundx) {
							xx = boundx;
							h = Math.abs((xx - x1) / aspect);
							yy = rh < 0 ? y1 - h: h + y1
						}
					}
				} else {
					xx = x2;
					h = rwa / aspect;
					yy = rh < 0 ? y1 - h: y1 + h;
					if (yy < 0) {
						yy = 0;
						w = Math.abs((yy - y1) * aspect);
						xx = rw < 0 ? x1 - w: w + x1
					} else {
						if (yy > boundy) {
							yy = boundy;
							w = Math.abs(yy - y1) * aspect;
							xx = rw < 0 ? x1 - w: w + x1
						}
					}
				}
				if (xx > x1) {
					if (xx - x1 < min_x) {
						xx = x1 + min_x
					} else {
						if (xx - x1 > max_x) {
							xx = x1 + max_x
						}
					}
					if (yy > y1) {
						yy = y1 + (xx - x1) / aspect
					} else {
						yy = y1 - (xx - x1) / aspect
					}
				} else {
					if (xx < x1) {
						if (x1 - xx < min_x) {
							xx = x1 - min_x
						} else {
							if (x1 - xx > max_x) {
								xx = x1 - max_x
							}
						}
						if (yy > y1) {
							yy = y1 + (x1 - xx) / aspect
						} else {
							yy = y1 - (x1 - xx) / aspect
						}
					}
				}
				if (xx < 0) {
					x1 -= xx;
					xx = 0
				} else {
					if (xx > boundx) {
						x1 -= xx - boundx;
						xx = boundx
					}
				}
				if (yy < 0) {
					y1 -= yy;
					yy = 0
				} else {
					if (yy > boundy) {
						y1 -= yy - boundy;
						yy = boundy
					}
				}
				return last = makeObj(flipCoords(x1, y1, xx, yy))
			}
			function rebound(p) {
				if (p[0] < 0) {
					p[0] = 0
				}
				if (p[1] < 0) {
					p[1] = 0
				}
				if (p[0] > boundx) {
					p[0] = boundx
				}
				if (p[1] > boundy) {
					p[1] = boundy
				}
				return [p[0], p[1]]
			}
			function flipCoords(x1, y1, x2, y2) {
				var xa = x1,
				xb = x2,
				ya = y1,
				yb = y2;
				if (x2 < x1) {
					xa = x2;
					xb = x1
				}
				if (y2 < y1) {
					ya = y2;
					yb = y1
				}
				return [Math.round(xa), Math.round(ya), Math.round(xb), Math.round(yb)]
			}
			function getRect() {
				var xsize = x2 - x1;
				var ysize = y2 - y1;
				if (xlimit && (Math.abs(xsize) > xlimit)) {
					x2 = (xsize > 0) ? (x1 + xlimit) : (x1 - xlimit)
				}
				if (ylimit && (Math.abs(ysize) > ylimit)) {
					y2 = (ysize > 0) ? (y1 + ylimit) : (y1 - ylimit)
				}
				if (ymin && (Math.abs(ysize) < ymin)) {
					y2 = (ysize > 0) ? (y1 + ymin) : (y1 - ymin)
				}
				if (xmin && (Math.abs(xsize) < xmin)) {
					x2 = (xsize > 0) ? (x1 + xmin) : (x1 - xmin)
				}
				if (x1 < 0) {
					x2 -= x1;
					x1 -= x1
				}
				if (y1 < 0) {
					y2 -= y1;
					y1 -= y1
				}
				if (x2 < 0) {
					x1 -= x2;
					x2 -= x2
				}
				if (y2 < 0) {
					y1 -= y2;
					y2 -= y2
				}
				if (x2 > boundx) {
					var delta = x2 - boundx;
					x1 -= delta;
					x2 -= delta
				}
				if (y2 > boundy) {
					var delta = y2 - boundy;
					y1 -= delta;
					y2 -= delta
				}
				if (x1 > boundx) {
					var delta = x1 - boundy;
					y2 -= delta;
					y1 -= delta
				}
				if (y1 > boundy) {
					var delta = y1 - boundy;
					y2 -= delta;
					y1 -= delta
				}
				return makeObj(flipCoords(x1, y1, x2, y2))
			}
			function makeObj(a) {
				return {
					x: a[0],
					y: a[1],
					x2: a[2],
					y2: a[3],
					w: a[2] - a[0],
					h: a[3] - a[1]
				}
			}
			return {
				flipCoords: flipCoords,
				setPressed: setPressed,
				setCurrent: setCurrent,
				getOffset: getOffset,
				moveOffset: moveOffset,
				getCorner: getCorner,
				getFixed: getFixed
			}
		} ();
		var Selection = function() {
			var start, end, dragmode, awake, hdep = 370;
			var borders = {};
			var handle = {};
			var seehandles = false;
			var hhs = options.handleOffset;
			if (options.drawBorders) {
				borders = {
					top: insertBorder("hline").css("top", $.browser.msie ? px( - 1) : px(0)),
					bottom: insertBorder("hline"),
					left: insertBorder("vline"),
					right: insertBorder("vline")
				}
			}
			if (options.dragEdges) {
				handle.t = insertDragbar("n");
				handle.b = insertDragbar("s");
				handle.r = insertDragbar("e");
				handle.l = insertDragbar("w")
			}
			options.sideHandles && createHandles(["n", "s", "e", "w"]);
			options.cornerHandles && createHandles(["sw", "nw", "ne", "se"]);
			function insertBorder(type) {
				var jq = $("<div />").css({
					position: "absolute",
					opacity: options.borderOpacity
				}).addClass(cssClass(type));
				$img_holder.append(jq);
				return jq
			}
			function dragDiv(ord, zi) {
				var jq = $("<div />").mousedown(createDragger(ord)).css({
					cursor: ord + "-resize",
					position: "absolute",
					zIndex: zi
				});
				$hdl_holder.append(jq);
				return jq
			}
			function insertHandle(ord) {
				return dragDiv(ord, hdep++).css({
					top: px( - hhs + 1),
					left: px( - hhs + 1),
					opacity: options.handleOpacity
				}).addClass(cssClass("handle"))
			}
			function insertDragbar(ord) {
				var s = options.handleSize,
				o = hhs,
				h = s,
				w = s,
				t = o,
				l = o;
				switch (ord) {
				case "n":
				case "s":
					w = pct(100);
					break;
				case "e":
				case "w":
					h = pct(100);
					break
				}
				return dragDiv(ord, hdep++).width(w).height(h).css({
					top: px( - t + 1),
					left: px( - l + 1)
				})
			}
			function createHandles(li) {
				for (i in li) {
					handle[li[i]] = insertHandle(li[i])
				}
			}
			function moveHandles(c) {
				var midvert = Math.round((c.h / 2) - hhs),
				midhoriz = Math.round((c.w / 2) - hhs),
				north = west = -hhs + 1,
				east = c.w - hhs,
				south = c.h - hhs,
				x,
				y;
				"e" in handle && handle.e.css({
					top: px(midvert),
					left: px(east)
				}) && handle.w.css({
					top: px(midvert)
				}) && handle.s.css({
					top: px(south),
					left: px(midhoriz)
				}) && handle.n.css({
					left: px(midhoriz)
				});
				"ne" in handle && handle.ne.css({
					left: px(east)
				}) && handle.se.css({
					top: px(south),
					left: px(east)
				}) && handle.sw.css({
					top: px(south)
				});
				"b" in handle && handle.b.css({
					top: px(south)
				}) && handle.r.css({
					left: px(east)
				})
			}
			function moveto(x, y) {
				$img2.css({
					top: px( - y),
					left: px( - x)
				});
				$sel.css({
					top: px(y),
					left: px(x)
				})
			}
			function resize(w, h) {
				$sel.width(w).height(h)
			}
			function refresh() {
				var c = Coords.getFixed();
				Coords.setPressed([c.x, c.y]);
				Coords.setCurrent([c.x2, c.y2]);
				updateVisible()
			}
			function updateVisible() {
				if (awake) {
					return update()
				}
			}
			function update() {
				var c = Coords.getFixed();
				resize(c.w, c.h);
				moveto(c.x, c.y);
				options.drawBorders && borders["right"].css({
					left: px(c.w - 1)
				}) && borders["bottom"].css({
					top: px(c.h - 1)
				});
				seehandles && moveHandles(c);
				awake || show();
				options.onChange(unscale(c))
			}
			function show() {
				$sel.show();
				$img.css("opacity", options.bgOpacity);
				awake = true
			}
			function release() {
				disableHandles();
				$sel.hide();
				$img.css("opacity", 1);
				awake = false
			}
			function showHandles() {
				if (seehandles) {
					moveHandles(Coords.getFixed());
					$hdl_holder.show()
				}
			}
			function enableHandles() {
				seehandles = true;
				if (options.allowResize) {
					moveHandles(Coords.getFixed());
					$hdl_holder.show();
					return true
				}
			}
			function disableHandles() {
				seehandles = false;
				$hdl_holder.hide()
			}
			function animMode(v) { (animating = v) ? disableHandles() : enableHandles()
			}
			function done() {
				animMode(false);
				refresh()
			}
			var $track = newTracker().mousedown(createDragger("move")).css({
				cursor: "move",
				position: "absolute",
				zIndex: 360
			});
			$img_holder.append($track);
			disableHandles();
			return {
				updateVisible: updateVisible,
				update: update,
				release: release,
				refresh: refresh,
				setCursor: function(cursor) {
					$track.css("cursor", cursor)
				},
				enableHandles: enableHandles,
				enableOnly: function() {
					seehandles = true
				},
				showHandles: showHandles,
				disableHandles: disableHandles,
				animMode: animMode,
				done: done
			}
		} ();
		var Tracker = function() {
			var onMove = function() {},
			onDone = function() {},
			trackDoc = options.trackDocument;
			if (!trackDoc) {
				$trk.mousemove(trackMove).mouseup(trackUp).mouseout(trackUp)
			}
			function toFront() {
				$trk.css({
					zIndex: 450
				});
				if (trackDoc) {
					$(document).mousemove(trackMove).mouseup(trackUp)
				}
			}
			function toBack() {
				$trk.css({
					zIndex: 290
				});
				if (trackDoc) {
					$(document).unbind("mousemove", trackMove).unbind("mouseup", trackUp)
				}
			}
			function trackMove(e) {
				onMove(mouseAbs(e))
			}
			function trackUp(e) {
				e.preventDefault();
				e.stopPropagation();
				if (btndown) {
					btndown = false;
					onDone(mouseAbs(e));
					options.onSelect(unscale(Coords.getFixed()));
					toBack();
					onMove = function() {};
					onDone = function() {}
				}
				return false
			}
			function activateHandlers(move, done) {
				btndown = true;
				onMove = move;
				onDone = done;
				toFront();
				return false
			}
			function setCursor(t) {
				$trk.css("cursor", t)
			}
			$img.before($trk);
			return {
				activateHandlers: activateHandlers,
				setCursor: setCursor
			}
		} ();
		var KeyManager = function() {
			var $keymgr = $('<input type="radio" />').css({
				position: "absolute",
				left: "-30px"
			}).keypress(parseKey).blur(onBlur),
			$keywrap = $("<div />").css({
				position: "absolute",
				overflow: "hidden"
			}).append($keymgr);
			function watchKeys() {
				if (options.keySupport) {
					$keymgr.show();
					$keymgr.focus()
				}
			}
			function onBlur(e) {
				$keymgr.hide()
			}
			function doNudge(e, x, y) {
				if (options.allowMove) {
					Coords.moveOffset([x, y]);
					Selection.updateVisible()
				}
				e.preventDefault();
				e.stopPropagation()
			}
			function parseKey(e) {
				if (e.ctrlKey) {
					return true
				}
				shift_down = e.shiftKey ? true: false;
				var nudge = shift_down ? 10 : 1;
				switch (e.keyCode) {
				case 37:
					doNudge(e, -nudge, 0);
					break;
				case 39:
					doNudge(e, nudge, 0);
					break;
				case 38:
					doNudge(e, 0, -nudge);
					break;
				case 40:
					doNudge(e, 0, nudge);
					break;
				case 27:
					Selection.release();
					break;
				case 9:
					return true
				}
				return nothing(e)
			}
			if (options.keySupport) {
				$keywrap.insertBefore($img)
			}
			return {
				watchKeys:
				watchKeys
			}
		} ();
		function px(n) {
			return "" + parseInt(n) + "px"
		}
		function pct(n) {
			return "" + parseInt(n) + "%"
		}
		function cssClass(cl) {
			return options.baseClass + "-" + cl
		}
		function getPos(obj) {
			var pos = $(obj).offset();
			return [pos.left, pos.top]
		}
		function mouseAbs(e) {
			return [(e.pageX - docOffset[0]), (e.pageY - docOffset[1])]
		}
		function myCursor(type) {
			if (type != lastcurs) {
				Tracker.setCursor(type);
				lastcurs = type
			}
		}
		function startDragMode(mode, pos) {
			docOffset = getPos($img);
			Tracker.setCursor(mode == "move" ? mode: mode + "-resize");
			if (mode == "move") {
				return Tracker.activateHandlers(createMover(pos), doneSelect)
			}
			var fc = Coords.getFixed();
			var opp = oppLockCorner(mode);
			var opc = Coords.getCorner(oppLockCorner(opp));
			Coords.setPressed(Coords.getCorner(opp));
			Coords.setCurrent(opc);
			Tracker.activateHandlers(dragmodeHandler(mode, fc), doneSelect)
		}
		function dragmodeHandler(mode, f) {
			return function(pos) {
				if (!options.aspectRatio) {
					switch (mode) {
					case "e":
						pos[1] = f.y2;
						break;
					case "w":
						pos[1] = f.y2;
						break;
					case "n":
						pos[0] = f.x2;
						break;
					case "s":
						pos[0] = f.x2;
						break
					}
				} else {
					switch (mode) {
					case "e":
						pos[1] = f.y + 1;
						break;
					case "w":
						pos[1] = f.y + 1;
						break;
					case "n":
						pos[0] = f.x + 1;
						break;
					case "s":
						pos[0] = f.x + 1;
						break
					}
				}
				Coords.setCurrent(pos);
				Selection.update()
			}
		}
		function createMover(pos) {
			var lloc = pos;
			KeyManager.watchKeys();
			return function(pos) {
				Coords.moveOffset([pos[0] - lloc[0], pos[1] - lloc[1]]);
				lloc = pos;
				Selection.update()
			}
		}
		function oppLockCorner(ord) {
			switch (ord) {
			case "n":
				return "sw";
			case "s":
				return "nw";
			case "e":
				return "nw";
			case "w":
				return "ne";
			case "ne":
				return "sw";
			case "nw":
				return "se";
			case "se":
				return "nw";
			case "sw":
				return "ne"
			}
		}
		function createDragger(ord) {
			return function(e) {
				if (options.disabled) {
					return false
				}
				if ((ord == "move") && !options.allowMove) {
					return false
				}
				btndown = true;
				startDragMode(ord, mouseAbs(e));
				e.stopPropagation();
				e.preventDefault();
				return false
			}
		}
		function presize($obj, w, h) {
			var nw = $obj.width(),
			nh = $obj.height();
			if ((nw > w) && w > 0) {
				nw = w;
				nh = (w / $obj.width()) * $obj.height()
			}
			if ((nh > h) && h > 0) {
				nh = h;
				nw = (h / $obj.height()) * $obj.width()
			}
			xscale = $obj.width() / nw;
			yscale = $obj.height() / nh;
			$obj.width(nw).height(nh)
		}
		function unscale(c) {
			return {
				x: parseInt(c.x * xscale),
				y: parseInt(c.y * yscale),
				x2: parseInt(c.x2 * xscale),
				y2: parseInt(c.y2 * yscale),
				w: parseInt(c.w * xscale),
				h: parseInt(c.h * yscale)
			}
		}
		function doneSelect(pos) {
			var c = Coords.getFixed();
			if (c.w > options.minSelect[0] && c.h > options.minSelect[1]) {
				Selection.enableHandles();
				Selection.done()
			} else {
				Selection.release()
			}
			Tracker.setCursor(options.allowSelect ? "crosshair": "default")
		}
		function newSelection(e) {
			if (options.disabled) {
				return false
			}
			if (!options.allowSelect) {
				return false
			}
			btndown = true;
			docOffset = getPos($img);
			Selection.disableHandles();
			myCursor("crosshair");
			var pos = mouseAbs(e);
			Coords.setPressed(pos);
			Tracker.activateHandlers(selectDrag, doneSelect);
			KeyManager.watchKeys();
			Selection.update();
			e.stopPropagation();
			e.preventDefault();
			return false
		}
		function selectDrag(pos) {
			Coords.setCurrent(pos);
			Selection.update()
		}
		function newTracker() {
			var trk = $("<div></div>").addClass(cssClass("tracker"));
			$.browser.msie && trk.css({
				opacity: 0,
				backgroundColor: "white"
			});
			return trk
		}
		function animateTo(a) {
			var x1 = a[0] / xscale,
			y1 = a[1] / yscale,
			x2 = a[2] / xscale,
			y2 = a[3] / yscale;
			if (animating) {
				return
			}
			var animto = Coords.flipCoords(x1, y1, x2, y2);
			var c = Coords.getFixed();
			var animat = initcr = [c.x, c.y, c.x2, c.y2];
			var interv = options.animationDelay;
			var x = animat[0];
			var y = animat[1];
			var x2 = animat[2];
			var y2 = animat[3];
			var ix1 = animto[0] - initcr[0];
			var iy1 = animto[1] - initcr[1];
			var ix2 = animto[2] - initcr[2];
			var iy2 = animto[3] - initcr[3];
			var pcent = 0;
			var velocity = options.swingSpeed;
			Selection.animMode(true);
			var animator = function() {
				return function() {
					pcent += (100 - pcent) / velocity;
					animat[0] = x + ((pcent / 100) * ix1);
					animat[1] = y + ((pcent / 100) * iy1);
					animat[2] = x2 + ((pcent / 100) * ix2);
					animat[3] = y2 + ((pcent / 100) * iy2);
					if (pcent < 100) {
						animateStart()
					} else {
						Selection.done()
					}
					if (pcent >= 99.8) {
						pcent = 100
					}
					setSelectRaw(animat)
				}
			} ();
			function animateStart() {
				window.setTimeout(animator, interv)
			}
			animateStart()
		}
		function setSelect(rect) {
			setSelectRaw([rect[0] / xscale, rect[1] / yscale, rect[2] / xscale, rect[3] / yscale])
		}
		function setSelectRaw(l) {
			Coords.setPressed([l[0], l[1]]);
			Coords.setCurrent([l[2], l[3]]);
			Selection.update()
		}
		function setOptions(opt) {
			if (typeof(opt) != "object") {
				opt = {}
			}
			options = $.extend(options, opt);
			if (typeof(options.onChange) !== "function") {
				options.onChange = function() {}
			}
			if (typeof(options.onSelect) !== "function") {
				options.onSelect = function() {}
			}
		}
		function tellSelect() {
			return unscale(Coords.getFixed())
		}
		function tellScaled() {
			return Coords.getFixed()
		}
		function setOptionsNew(opt) {
			setOptions(opt);
			interfaceUpdate()
		}
		function disableCrop() {
			options.disabled = true;
			Selection.disableHandles();
			Selection.setCursor("default");
			Tracker.setCursor("default")
		}
		function enableCrop() {
			options.disabled = false;
			interfaceUpdate()
		}
		function cancelCrop() {
			Selection.done();
			Tracker.activateHandlers(null, null)
		}
		function destroy() {
			$div.remove();
			$origimg.show()
		}
		function interfaceUpdate(alt) {
			options.allowResize ? alt ? Selection.enableOnly() : Selection.enableHandles() : Selection.disableHandles();
			Tracker.setCursor(options.allowSelect ? "crosshair": "default");
			Selection.setCursor(options.allowMove ? "move": "default");
			$div.css("backgroundColor", options.bgColor);
			if ("setSelect" in options) {
				setSelect(opt.setSelect);
				Selection.done();
				delete(options.setSelect)
			}
			if ("trueSize" in options) {
				xscale = options.trueSize[0] / boundx;
				yscale = options.trueSize[1] / boundy
			}
			xlimit = options.maxSize[0] || 0;
			ylimit = options.maxSize[1] || 0;
			xmin = options.minSize[0] || 0;
			ymin = options.minSize[1] || 0;
			if ("outerImage" in options) {
				$img.attr("src", options.outerImage);
				delete(options.outerImage)
			}
			Selection.refresh()
		}
		$hdl_holder.hide();
		interfaceUpdate(true);
		var api = {
			animateTo: animateTo,
			setSelect: setSelect,
			setOptions: setOptionsNew,
			tellSelect: tellSelect,
			tellScaled: tellScaled,
			disable: disableCrop,
			enable: enableCrop,
			cancel: cancelCrop,
			focus: KeyManager.watchKeys,
			getBounds: function() {
				return [boundx * xscale, boundy * yscale]
			},
			getWidgetSize: function() {
				return [boundx, boundy]
			},
			release: Selection.release,
			destroy: destroy
		};
		$origimg.data("Jcrop", api);
		return api
	};
	$.fn.Jcrop = function(options) {
		function attachWhenDone(from) {
			var loadsrc = options.useImg || from.src;
			var img = new Image();
			img.onload = function() {
				$.Jcrop(from, options)
			};
			img.src = loadsrc
		}
		if (typeof(options) !== "object") {
			options = {}
		}
		this.each(function() {
			if ($(this).data("Jcrop")) {
				if (options == "api") {
					return $(this).data("Jcrop")
				} else {
					$(this).data("Jcrop").setOptions(options)
				}
			} else {
				attachWhenDone(this)
			}
		});
		return this
	}
})(jQuery); (function($, undefined) {
	$.fn.zyPopup = function(options, param) {
		var otherArgs = Array.prototype.slice.call(arguments, 1);
		if (typeof options == "string") {
			var fn = this[0][options];
			if ($.isFunction(fn)) {
				return fn.apply(this, otherArgs)
			} else {
				throw ("zyPopup - No such method: " + options)
			}
		}
		return this.each(function() {
			var para = {};
			var self = this;
			var zoom = "",
			zoomContent = "",
			zoomedIn = false,
			openedImage = null,
			windowWidth = "",
			windowHeight = "";
			var tailorVal = {};
			var defaults = {
				src: "",
				index: 0,
				name: "",
				onTailor: function(val) {}
			};
			para = $.extend(defaults, options);
			this.init = function() {
				this.createHtml();
				this.openPopup();
				this.bindPopupEvent()
			};
			this.createHtml = function() {
				$("#zoom").remove();
				$("body").append('<div id="zoom"><a class="finish"></a><a class="close"></a><div class="content loading"></div></div>');
				zoom = $("#zoom").hide(),
				zoomContent = $("#zoom .content"),
				zoomedIn = false,
				openedImage = null,
				windowWidth = $(window).width(),
				windowHeight = $(window).height()
			};
			this.openPopup = function() {
				var self = this;
				var image = $(new Image()).attr("id", "tailorImg").hide();
				$("#zoom .previous, #zoom .next").show();
				if (!zoomedIn) {
					zoomedIn = true;
					zoom.show();
					$("body").addClass("zoomed")
				}
				zoomContent.html(image).delay(500).addClass("loading");
				image.load(render).attr("src", para.src);
				var editWidth = $("#zoom").width();
				var editHeight = $("#zoom").height();
                var xScale = 1;
                var yScale = 1;
                if(editWidth < image.width()){
                    xScale = editWidth/image.width();
                }
                if(editHeight < image.height()){
                    yScale = editHeight/image.height();
                }
                if(xScale > yScale){
                    xScale = yScale;
                    Scale=xScale;
                }

				function render() {
					var image = $(this),
					borderWidth = parseInt(zoomContent.css("borderLeftWidth")),
					maxImageWidth = windowWidth - (borderWidth * 2),
					maxImageHeight = windowHeight - (borderWidth * 2),					
					imageWidth = image.width(),
					imageHeight = image.height();
					if (imageWidth == zoomContent.width() && imageWidth <= maxImageWidth && imageHeight == zoomContent.height() && imageHeight <= maxImageHeight) {
						show(image);
						return
					}
					zoomContent.animate({
						width: image.width()*xScale,
						height: image.height()*xScale,
						marginTop: -(image.height()*xScale / 2) - borderWidth,
						marginLeft: -(image.width()*xScale / 2) - borderWidth
					},
					200,
					function() {
						show(image)
					});
					function show(image) {
						
						image.show();
						zoomContent.removeClass("loading");
						self.createTailorPlug()
					}
				}
			};
			this.createTailorPlug = function() {
				var width = $("#tailorImg").width();
				var height = $("#tailorImg").height();
				
				var editWidth = $("#zoom").width();
				var editHeight = $("#zoom").height();
                var xScale = 1;
                var yScale = 1;
                if(editWidth < width){
                    xScale = editWidth/width;
                }
                if(editHeight < height){
                    yScale = editHeight/height;
                }
                if(xScale > yScale){
                    xScale = yScale;
                    Scale=xScale;
                }
                ScaleWidth=width*xScale;
				$("#tailorImg").css("width",width*xScale);
				$("#tailorImg").css("height",height*xScale);
				$("#tailorImg").css("position","relative");
                var x1 = (width*Scale/2)-(width*Scale/5);
                var y1 = (height*Scale/2)-(height*Scale/5);
                var x2 = (width*Scale/2)+(width*Scale/5);
                var y2 = (height*Scale/2)+(height*Scale/5);
	
				
				
				var api = $.Jcrop("#tailorImg", {
					setSelect: [x1,y1, x2,y2],
					onChange: setCoords,
					onSelect: setCoords,					
					allowResize:true,
					keySupport:true,
					allowMove:true
					
				});
				function setCoords(obj) {
					
					tailorVal = {
						"leftX": obj.x,
						"leftY": obj.y,
						"rightX": obj.x2,
						"rightY": obj.y2,
						"width": obj.w,
						"height": obj.h
					}
					
				}				
				
			};
			this.bindPopupEvent = function() {
				var self = this;
				zoom.bind("click",
				function(event) {
					event.preventDefault();
					if ($(event.target).attr("id") == "zoom") {
						self.closePopup(event)
					}
				});
				$("#zoom .finish").bind("click",
				function(event) {
					var width = $("#tailorImg").width();				    
				    var editWidth = $("#zoom").width();
				    var xScale = editWidth/width;				
					var quondamImgInfo = {};
					quondamImgInfo["width"] = $(".jcrop-holder>div>div>img").width();
					quondamImgInfo["height"] = $(".jcrop-holder>div>div>img").height();
					para.onTailor(tailorVal, quondamImgInfo);
					self.closePopup(event)
				});
				$("#zoom .close").bind("click",
				function(event) {
					self.closePopup(event)
				})
			};
			this.closePopup = function(event) {
				if (event) {
					event.preventDefault()
				}
				zoomedIn = false;
				openedImage = null;
				zoom.hide();
				$("body").removeClass("zoomed");
				zoomContent.empty()
			};
			this.init()
		})
	}
})(jQuery);
var ZYFILE = {
	fileInput: null,
	uploadInput: null,
	dragDrop: null,
	url: "",
	uploadFile: [],
	lastUploadFile: [],
	perUploadFile: [],
	fileNum: 0,
	filterFile: function(files) {
		return files
	},
	onSelect: function(selectFile, files) {},
	onDelete: function(file, files) {},
	onProgress: function(file, loaded, total) {},
	onSuccess: function(file, responseInfo) {},
	onFailure: function(file, responseInfo) {},
	onComplete: function(responseInfo) {},
	funDragHover: function(e) {
		e.stopPropagation();
		e.preventDefault();
		this[e.type === "dragover" ? "onDragOver": "onDragLeave"].call(e.target);
		return this
	},
	funGetFiles: function(e) {
		var self = this;
		this.funDragHover(e);
		var files = e.target.files || e.dataTransfer.files;
		self.lastUploadFile = this.uploadFile;
		this.uploadFile = this.uploadFile.concat(this.filterFile(files));
		var tmpFiles = [];
		var lArr = [];
		var uArr = [];
		$.each(self.lastUploadFile,
		function(k, v) {
			lArr.push(v.name)
		});
		$.each(self.uploadFile,
		function(k, v) {
			uArr.push(v.name)
		});
		$.each(uArr,
		function(k, v) {
			if ($.inArray(v, lArr) < 0) {
				tmpFiles.push(self.uploadFile[k])
			}
		});
		this.uploadFile = tmpFiles;
		this.funDealtFiles();
		return true
	},
	funDealtFiles: function() {
		var self = this;
		$.each(this.uploadFile,
		function(k, v) {
			v.index = self.fileNum;
			self.fileNum++
		});
		var selectFile = this.uploadFile;
		this.perUploadFile = this.perUploadFile.concat(this.uploadFile);
		this.uploadFile = this.lastUploadFile.concat(this.uploadFile);
		this.onSelect(selectFile, this.uploadFile);
		console.info("继续选择");
		console.info(this.uploadFile);
		return this
	},
	funDeleteFile: function(delFileIndex, isCb) {
		var self = this;
		var tmpFile = [];
		var delFile = this.perUploadFile[delFileIndex];
		$.each(this.uploadFile,
		function(k, v) {
			if (delFile != v) {
				tmpFile.push(v)
			}
		});
		this.uploadFile = tmpFile;
		if (isCb) {
			self.onDelete(delFile, this.uploadFile)
		}
		console.info("还剩这些文件没有上传:");
		console.info(this.uploadFile);
		return true
	},
	funUploadFiles: function() {
		var self = this;
		$.each(this.uploadFile,
		function(k, v) {
			self.funUploadFile(v)
		})
	},
	funUploadFile: function(file) {
		var self = this;
		var formdata = new FormData();
		var width = ScaleWidth;
		if(!width){
			width = 0;
		}
		formdata.append("tailorWidth", width);
		formdata.append("file", file);
		if ($("#uploadTailor_" + file.index).length > 0) {
			formdata.append("tailor", $("#uploadTailor_" + file.index).attr("tailor"))
		}
		var xhr = new XMLHttpRequest();
		xhr.upload.addEventListener("progress",
		function(e) {
			self.onProgress(file, e.loaded, e.total)
		},
		false);
		xhr.addEventListener("load",
		function(e) {
			self.funDeleteFile(file.index, false);
			self.onSuccess(file, xhr.responseText);
			if (self.uploadFile.length == 0) {
				self.onComplete("全部完成")
			}
		},
		false);
		xhr.addEventListener("error",
		function(e) {
			self.onFailure(file, xhr.responseText)
		},
		false);
		xhr.open("POST", self.url, true);
		xhr.send(formdata)
	},
	funReturnNeedFiles: function() {
		return this.uploadFile
	},
	init: function() {
		var self = this;
		if (this.dragDrop) {
			this.dragDrop.addEventListener("dragover",
			function(e) {
				self.funDragHover(e)
			},
			false);
			this.dragDrop.addEventListener("dragleave",
			function(e) {
				self.funDragHover(e)
			},
			false);
			this.dragDrop.addEventListener("drop",
			function(e) {
				self.funGetFiles(e)
			},
			false)
		}
		if (self.fileInput) {
			this.fileInput.addEventListener("change",
			function(e) {
				self.funGetFiles(e)
			},
			false)
		}
		if (self.uploadInput) {
			this.uploadInput.addEventListener("click",
			function(e) {
				self.funUploadFiles(e)
			},
			false)
		}
	}
}; (function($, undefined) {
	$.fn.zyUpload = function(options, param) {
		var otherArgs = Array.prototype.slice.call(arguments, 1);
		if (typeof options == "string") {
			var fn = this[0][options];
			if ($.isFunction(fn)) {
				return fn.apply(this, otherArgs)
			} else {
				throw ("zyUpload - No such method: " + options)
			}
		}
		return this.each(function() {
			var para = {};
			var self = this;
			var defaults = {
				width: "700px",
				height: "400px",
				itemWidth: "140px",
				itemHeight: "120px",
				url: "/upload/UploadAction",
				fileType: [],
				fileSize: 51200000,
				multiple: true,
				dragDrop: true,
				tailor: false,
				del: true,
				finishDel: false,
				onSelect: function(selectFiles, allFiles) {},
				onDelete: function(file, files) {},
				onSuccess: function(file, response) {},
				onFailure: function(file, response) {},
				onComplete: function(response) {}
			};
			para = $.extend(defaults, options);
			this.init = function() {
				this.createHtml();
				this.createCorePlug()
			};
			this.createHtml = function() {
				var multiple = "";
				para.multiple ? multiple = "multiple": multiple = "";
				var html = "";
				if (para.dragDrop) {
					html += '<form id="uploadForm" action="' + para.url + '" method="post" enctype="multipart/form-data">';
					html += '	<div class="upload_box">';
					html += '		<div class="upload_main">';
					html += '			<div class="upload_choose">';
					html += '				<div class="convent_choice">';
					html += '					<div class="andArea">';
					html += '						<div class="filePicker">点击选择文件</div>';
					html += '						<input id="fileImage" type="file" size="30" name="fileselect[]" ' + multiple + ">";
					html += "					</div>";
					html += "				</div>";
					html += '				<span id="fileDragArea" class="upload_drag_area">或者将文件拖到此处</span>';
					html += "			</div>";
					html += '			<div class="status_bar">';
					html += '				<div id="status_info" class="info">选中0张文件，共0B。</div>';
					html += '				<div class="btns">';
					html += '					<div class="webuploader_pick">继续选择</div>';
					html += '					<div class="upload_btn">开始上传</div>';
					html += "				</div>";
					html += "			</div>";
					html += '			<div id="preview" class="upload_preview"></div>';
					html += "		</div>";
					html += '		<div class="upload_submit">';
					html += '			<button type="button" id="fileSubmit" class="upload_submit_btn">确认上传文件</button>';
					html += "		</div>";
					html += '		<div id="uploadInf" class="upload_inf"></div>';
					html += "	</div>";
					html += "</form>"
				} else {
					var imgWidth = parseInt(para.itemWidth.replace("px", "")) - 15;
					html += '<form id="uploadForm" action="' + para.url + '" method="post" enctype="multipart/form-data">';
					html += '	<div class="upload_box">';
					html += '		<div class="upload_main single_main">';
					html += '			<div class="status_bar">';
					html += '				<div id="status_info" class="info">选中0张文件，共0B。</div>';
					html += '				<div class="btns">';
					html += '					<input id="fileImage" type="file" size="30" name="fileselect[]" ' + multiple + ">";
					html += '					<div class="webuploader_pick">选择文件</div>';
					html += '					<div class="upload_btn">开始上传</div>';
					html += "				</div>";
					html += "			</div>";
					html += '			<div id="preview" class="upload_preview">';
					html += '				<div class="add_upload">';
					html += '					<a style="height:' + para.itemHeight + ";width:" + para.itemWidth + ';" title="点击添加文件" id="rapidAddImg" class="add_imgBox" href="javascript:void(0)">';
					html += '						<div class="uploadImg" style="width:' + imgWidth + 'px">';
					html += '							<img class="upload_image" src="'+basePath+'/Olive/plugin/zyupload/skins/images/add_img.png" style="width:expression(this.width > ' + imgWidth + " ? " + imgWidth + 'px : this.width)" />';
					html += "						</div>";
					html += "					</a>";
					html += "				</div>";
					html += "			</div>";
					html += "		</div>";
					html += '		<div class="upload_submit">';
					html += '			<button type="button" id="fileSubmit" class="upload_submit_btn">确认上传文件</button>';
					html += "		</div>";
					html += '		<div id="uploadInf" class="upload_inf"></div>';
					html += "	</div>";
					html += "</form>"
				}
				$(self).append(html).css({
					"width": para.width,
					"height": para.height
				});
				this.addEvent()
			};
			this.funSetStatusInfo = function(files) {
				var size = 0;
				var num = files.length;
				$.each(files,
				function(k, v) {
					size += v.size
				});
				if (size > 1024 * 1024) {
					size = (Math.round(size * 100 / (1024 * 1024)) / 100).toString() + "MB"
				} else {
					size = (Math.round(size * 100 / 1024) / 100).toString() + "KB"
				}
				$("#status_info").html("选中" + num + "张文件，共" + size + "。")
			};
			this.funFilterEligibleFile = function(files) {
				var arrFiles = [];
				for (var i = 0,
				file; file = files[i]; i++) {
					var newStr = file.name.split("").reverse().join("");
					if (newStr.split(".")[0] != null) {
						var type = newStr.split(".")[0].split("").reverse().join("");
						if (jQuery.inArray(type, para.fileType) > -1) {
							if (file.size >= para.fileSize) {
								alert('您这个"' + file.name + '"文件大小过大')
							} else {
								arrFiles.push(file)
							}
						} else {
							alert('您这个"' + file.name + '"上传类型不符合')
						}
					} else {
						alert('您这个"' + file.name + '"没有类型, 无法识别')
					}
				}
				return arrFiles
			};
			this.funDisposePreviewHtml = function(file, e) {
				var html = "";
				var imgWidth = parseInt(para.itemWidth.replace("px", "")) - 15;
				var imgHeight = parseInt(para.itemHeight.replace("px", "")) - 10;
				var editHtml = "";
				var delHtml = "";
				if (para.tailor) {
					editHtml = '<span class="file_edit" data-index="' + file.index + '" title="编辑"></span>'
				}
				if (para.del) {
					delHtml = '<span class="file_del" data-index="' + file.index + '" title="删除"></span>'
				}
				var newStr = file.name.split("").reverse().join("");
				var type = newStr.split(".")[0].split("").reverse().join("");
				var fileImgSrc = basePath+"/Olive/plugin/"+"zyupload/skins/images/fileType/";
				if (type == "rar") {
					fileImgSrc = fileImgSrc + "rar.png"
				} else {
					if (type == "zip") {
						fileImgSrc = fileImgSrc + "zip.png"
					} else {
						if (type == "txt") {
							fileImgSrc = fileImgSrc + "txt.png"
						} else {
							if (type == "ppt") {
								fileImgSrc = fileImgSrc + "ppt.png"
							} else {
								if (type == "xls") {
									fileImgSrc = fileImgSrc + "xls.png"
								} else {
									if (type == "pdf") {
										fileImgSrc = fileImgSrc + "pdf.png"
									} else {
										if (type == "psd") {
											fileImgSrc = fileImgSrc + "psd.png"
										} else {
											if (type == "ttf") {
												fileImgSrc = fileImgSrc + "ttf.png"
											} else {
												if (type == "swf") {
													fileImgSrc = fileImgSrc + "swf.png"
												} else {
													fileImgSrc = fileImgSrc + "file.png"
												}
											}
										}
									}
								}
							}
						}
					}
				}
				if (file.type.indexOf("image") == 0) {
					html += '<div id="uploadList_' + file.index + '" class="upload_append_list">';
					html += '	<div class="file_bar">';
					html += '		<div style="padding:5px;">';
					html += '			<p class="file_name" title="' + file.name + '">' + file.name + "</p>";
					html += editHtml;
					html += delHtml;
					html += "		</div>";
					html += "	</div>";
					html += '	<a style="height:' + para.itemHeight + ";width:" + para.itemWidth + ';" href="#" class="imgBox">';
					html += '		<div class="uploadImg" style="width:' + imgWidth + "px;max-width:" + imgWidth + "px;max-height:" + imgHeight + 'px;">';
					html += '			<img id="uploadImage_' + file.index + '" class="upload_image" src="' + e.target.result + '" style="width:expression(this.width > ' + imgWidth + " ? " + imgWidth + 'px : this.width);" />';
					html += "		</div>";
					html += "	</a>";
					html += '	<p id="uploadProgress_' + file.index + '" class="file_progress"></p>';
					html += '	<p id="uploadFailure_' + file.index + '" class="file_failure">上传失败，请重试</p>';
					html += '	<p id="uploadTailor_' + file.index + '" class="file_tailor" tailor="false">裁剪完成</p>';
					html += '	<p id="uploadSuccess_' + file.index + '" class="file_success"></p>';
					html += "</div>"
				} else {
					html += '<div id="uploadList_' + file.index + '" class="upload_append_list">';
					html += '	<div class="file_bar">';
					html += '		<div style="padding:5px;">';
					html += '			<p class="file_name">' + file.name + "</p>";
					html += delHtml;
					html += "		</div>";
					html += "	</div>";
					html += '	<a style="height:' + para.itemHeight + ";width:" + para.itemWidth + ';" href="#" class="imgBox">';
					html += '		<div class="uploadImg" style="width:' + imgWidth + 'px">';
					html += '			<img id="uploadImage_' + file.index + '" class="upload_file" src="' + fileImgSrc + '" style="width:expression(this.width > ' + imgWidth + " ? " + imgWidth + 'px : this.width)" />';
					html += "		</div>";
					html += "	</a>";
					html += '	<p id="uploadProgress_' + file.index + '" class="file_progress"></p>';
					html += '	<p id="uploadFailure_' + file.index + '" class="file_failure">上传失败，请重试</p>';
					html += '	<p id="uploadSuccess_' + file.index + '" class="file_success"></p>';
					html += "</div>"
				}
				return html
			};
			this.createPopupPlug = function(imgSrc, index, name) {
				var editWidth = $("#zoom").width();
				var editHeight = $("#zoom").height();
				

					$("body").zyPopup({
					src: imgSrc,
					index: index,
					width:editWidth,
					name: name,					
					onTailor: function(val, quondamImgInfo) {
						var nWidth = parseInt(para.itemWidth.replace("px", ""));
						console.info("nWidth:"+nWidth);
						var nHeight = parseInt(para.itemHeight.replace("px", ""));
						console.info("nHeight:"+nHeight);
						var qWidth = val.width;
						console.info("qWidth:"+qWidth);
						var qHeight = val.height;
						console.info("qHeight:"+qHeight);
						var ratio = nWidth / qWidth;
						console.info("ratio:"+ratio);
						var width = ratio * quondamImgInfo.width;
						var height = ratio * quondamImgInfo.height;
						var left = val.leftX * ratio;
						var top = val.rightY * ratio - qHeight * ratio;
						$("#uploadImage_" + index).css({
							"width": width,
							"height": height,
							"margin-left": -left,
							"margin-top": -top
						});
						$("#uploadTailor_" + index).show();
						console.info(val);
						var tailor = "{'leftX':" + val.leftX + ",'leftY':" + val.leftY + ",'rightX':" + val.rightX + ",'rightY':" + val.rightY + ",'width':" + val.width + ",'height':" + val.height + "}";
						$("#uploadTailor_" + index).attr("tailor", tailor)
					}
				   })

				
			};
			this.createCorePlug = function() {
				var params = {
					fileInput: $("#fileImage").get(0),
					uploadInput: $("#fileSubmit").get(0),
					dragDrop: $("#fileDragArea").get(0),
					url: $("#uploadForm").attr("action"),
					filterFile: function(files) {
						return self.funFilterEligibleFile(files)
					},
					onSelect: function(selectFiles, allFiles) {
						para.onSelect(selectFiles, allFiles);
						self.funSetStatusInfo(ZYFILE.funReturnNeedFiles());
						var html = "",
						i = 0;
						var funDealtPreviewHtml = function() {
							file = selectFiles[i];
							if (file) {
								var reader = new FileReader();
								reader.onload = function(e) {
									html += self.funDisposePreviewHtml(file, e);
									i++;
									funDealtPreviewHtml()
								};
								reader.readAsDataURL(file)
							} else {
								funAppendPreviewHtml(html)
							}
						};
						var funAppendPreviewHtml = function(html) {
							if (para.dragDrop) {
								$("#preview").append(html)
							} else {
								$(".add_upload").before(html)
							}
							funBindDelEvent();
							funBindHoverEvent()
						};
						var funBindDelEvent = function() {
							if ($(".file_del").length > 0) {
								$(".file_del").click(function() {
									ZYFILE.funDeleteFile(parseInt($(this).attr("data-index")), true);
									return false
								})
							}
							if ($(".file_edit").length > 0) {
								$(".file_edit").click(function() {
									var imgIndex = $(this).attr("data-index");
									var imgName = $(this).prev(".file_name").attr("title");
									var imgSrc = $("#uploadImage_" + imgIndex).attr("src");
									self.createPopupPlug(imgSrc, imgIndex, imgName);
									return false
								})
							}
						};
						var funBindHoverEvent = function() {
							$(".upload_append_list").hover(function(e) {
								$(this).find(".file_bar").addClass("file_hover")
							},
							function(e) {
								$(this).find(".file_bar").removeClass("file_hover")
							})
						};
						funDealtPreviewHtml()
					},
					onDelete: function(file, files) {
						para.onDelete(file, files);
						$("#uploadList_" + file.index).fadeOut();
						self.funSetStatusInfo(files);
						console.info("剩下的文件");
						console.info(files)
					},
					onProgress: function(file, loaded, total) {
						var eleProgress = $("#uploadProgress_" + file.index),
						percent = (loaded / total * 100).toFixed(2) + "%";
						if (eleProgress.is(":hidden")) {
							eleProgress.show()
						}
						eleProgress.css("width", percent)
					},
					onSuccess: function(file, response) {
						para.onSuccess(file, response);
						$("#uploadProgress_" + file.index).hide();
						$("#uploadSuccess_" + file.index).show();
						if (para.finishDel) {
							$("#uploadList_" + file.index).fadeOut();
							self.funSetStatusInfo(ZYFILE.funReturnNeedFiles())
						}
						if ($("#uploadTailor_" + file.index).length > 0) {
							$("#uploadTailor_" + file.index).hide()
						}
					},
					onFailure: function(file, response) {
						para.onFailure(file, response);
						$("#uploadProgress_" + file.index).hide();
						$("#uploadSuccess_" + file.index).show();
						if ($("#uploadTailor_" + file.index).length > 0) {
							$("#uploadTailor_" + file.index).hide()
						}
						$("#uploadInf").append("<p>文件" + file.name + "上传失败！</p>")
					},
					onComplete: function(response) {
						para.onComplete(response);
						console.info(response)
					},
					onDragOver: function() {
						$(this).addClass("upload_drag_hover")
					},
					onDragLeave: function() {
						$(this).removeClass("upload_drag_hover")
					}
				};
				ZYFILE = $.extend(ZYFILE, params);
				ZYFILE.init()
			};
			this.addEvent = function() {
				if ($(".filePicker").length > 0) {
					$(".filePicker").bind("click",
					function(e) {
						$("#fileImage").click()
					})
				}
				$(".webuploader_pick").bind("click",
				function(e) {
					$("#fileImage").click()
				});
				$(".upload_btn").bind("click",
				function(e) {
					if (ZYFILE.funReturnNeedFiles().length > 0) {
						$("#fileSubmit").click()
					} else {
						alert("请先选中文件再点击上传")
					}
				});
				if ($("#rapidAddImg").length > 0) {
					$("#rapidAddImg").bind("click",
					function(e) {
						$("#fileImage").click()
					})
				}
			};
			this.init()
		})
	}
})(jQuery);