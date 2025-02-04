"use strict";(self.webpackChunk_N_E=self.webpackChunk_N_E||[]).push([[6377],{61077:(t,e,o)=>{o.d(e,{b:()=>td});var n=o(31085),a=o(80532),r=o(99917),i=o(14041),l=o(32133),s=o(73893),d=o(63587),c=o(94445),p=o(67397),m=o(33854),u=o(64225),h=o(80352),g=o(25420),y=o(95986),x=o(87747),f=o(65695),b=o(56824),v=o(31201),E=o(35687),k=o(70323),w=o(65998),j=o(23832),z=o(67063),_=o(97385);let S=t=>{let{editor:e}=t,o=(0,j.n)(_.Mk),a=(0,w.mt)(),[r,l]=i.useState(null),s=(0,i.useRef)(null),d={bg:(0,b.dU)("#F9FAFBDD","gray.900"),border:(0,b.dU)("whiteAlpha.600","whiteAlpha.100"),iconColor:(0,b.dU)("trueblue.600","trueblue.200"),dividerColor:(0,b.dU)("blackAlpha.200","whiteAlpha.200")};return(0,v.j)({ref:s,handler:t=>{t.target.closest("[data-insert-widget-popover]")||l(null)}}),(0,n.jsx)(n.Fragment,{children:(0,n.jsx)(E.B,{bg:d.bg,color:d.iconColor,p:.5,borderWidth:"1px",borderColor:d.border,borderRadius:"md",shadow:"lg",ref:s,overflow:"hidden",spacing:1,children:o.map((t,o)=>(0,n.jsxs)(i.Fragment,{children:[o>0&&(0,n.jsx)(k.c,{borderColor:d.dividerColor}),t.map(t=>{let{name:o,itemGroups:i,icon:s,featureFlag:d}=t;return d&&!a[d]?null:(0,n.jsx)(z.LX,{itemGroups:i,icon:s,name:o,editor:e,setOpenButton:l,isOpen:r===o},o)})]},o))})})},C=(0,i.memo)(t=>{let{editor:e}=t,o=(0,x.rd)();return(0,f.R)()?(0,n.jsx)(y.L8,{isDark:o,children:(0,n.jsx)(g.s,{position:"fixed",w:"0%",h:"0%",insetInlineEnd:4,top:"50%",alignItems:"center",zIndex:"sticky","data-testid":"insert-widget-wrapper",children:(0,n.jsx)(g.s,{position:"absolute",insetInlineEnd:0,"data-guider-highlight":"insert-widget",direction:"column",className:"insert-widget",children:(0,n.jsx)(S,{editor:e})})})}):null});var I=o(20277),A=o(57882),T=o(92667),F=o(83707),R=o(98100),B=o(44428),L=o(82354),U=o(69771);let G=function(){let t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"body";(0,i.useEffect)(()=>{let e;let o=document.querySelector(t);if(!o)return;let n=t=>{var n,a;(null===(n=document.activeElement)||void 0===n?void 0:n.tagName)==="IFRAME"&&document.activeElement instanceof HTMLIFrameElement&&!e?(console.debug("[EditorCore] Prevented scrolling at page load from iframe",t,document.activeElement),o.scrollTo(0,0),document.activeElement.blur()):(null===(a=document.activeElement)||void 0===a?void 0:a.tagName)==="IFRAME"&&0===t.target.scrollTop&&e>50?(console.debug("[EditorCore] Prevented scrolling to top from iframe",t,e,document.activeElement),o.scrollTo(0,e)):e=t.target.scrollTop};return o.addEventListener("scroll",n,{passive:!0}),()=>o.removeEventListener("scroll",n)},[t])};var M=o(2696),D=o(28895),P=o(71997),q=o(16372),O=o(58599),W=o(5933),N=o(65053),J=o(14e3),H=o(61778),$=o(18651),V=o(6256),X=o(80853),Y=o(23672),Z=o(15428),Q=o(52177),K=o(56154),tt=o(55090);class te extends i.Component{static getDerivedStateFromError(t){return{hasError:!0,errMessage:t.message}}componentDidCatch(t,e){this.errorEventId=K.Cp(t,{extra:{info:null==e?void 0:e.componentStack}}),Y.yf.logger.error("[EditorCoreErrorBoundary]",{event:tt.ng.EDITOR_CORE_ERROR,docId:this.props.docId},t)}render(){return this.state.hasError?(0,n.jsx)(tn,{errMessage:this.state.errMessage,errorId:this.errorEventId,isInsideDrawer:this.props.isInsideDrawer}):this.props.children}constructor(t){super(t),this.errorEventId=null,this.state={hasError:!1,errMessage:""}}}let to=["collaborative-editor-wrapper","public-static-editor-wrapper-inner","example-static-editor-wrapper-inner","custom-theme-tabs","custom-theme-tab-panels","custom-theme-preview-editor"].map(t=>'[data-testid="'.concat(t,'"]')).join(", "),tn=t=>{let{errMessage:e,errorId:o,isInsideDrawer:a=!1}=t;return(0,n.jsxs)(g.s,{minH:"100%",direction:"column","data-editor-error-component":!0,align:"center",children:[(0,n.jsx)(Z.mL,{styles:{[to]:{height:"100%"},html:{height:"100%"},body:{height:"100%"}}}),(0,n.jsxs)(E.B,{flex:1,align:"center",justify:"center",p:6,spacing:a?[4,4,4,6]:[6,10,12],w:a?["100%","100%","100%","container.sm","container.md"]:["100%","100%","container.md"],children:[(0,n.jsxs)(E.B,{textAlign:"center",children:[(0,n.jsx)(H.E,{fontSize:a?["xl","xl","2xl"]:["xl","2xl","3xl"],fontWeight:"600",fontFamily:"p22-mackinac-pro, sans-serif",children:(0,n.jsx)(Q.x6,{id:"gtI5JW"})}),!a&&(0,n.jsx)(H.E,{fontSize:"lg",children:(0,n.jsx)(Q.x6,{id:"9QTtU0"})})]}),(0,n.jsx)($._,{src:"https://cdn.gamma.app/zc87vhr30n8uf3n/b4b04402416f4ec8b5c1dc24f4c2a3d6/optimized/Sal-Spaceship-Riding.png",px:a?[4,4,4,8]:[6,6,10],maxW:a?"min(80%, 22rem)":"min(100%, 24rem)"}),e&&(0,n.jsxs)(V.C,{py:1,px:12,children:[e,o&&(0,n.jsx)(Q.x6,{id:"sS1P4/",values:{errorId:o},components:{0:(0,n.jsx)("br",{})}})]}),(0,n.jsxs)(E.B,{spacing:4,children:[!a&&(0,n.jsx)(X.$,{alignSelf:"center",variant:"solid",size:["md","md","lg"],onClick:()=>{window.location.reload()},children:(0,n.jsx)(Q.x6,{id:"C6f2hU"})}),(0,n.jsx)(H.E,{size:"sm",textAlign:"center",children:(0,n.jsx)(Q.x6,{id:"E4kJGH"})})]})]})]})};var ta=o(46595),tr=o(23529);let ti=t=>new Promise(e=>{setTimeout(()=>{requestAnimationFrame(()=>{let o=t.state.doc.resolve(1),n=(0,I.s)(o);n&&t.commands.command(t=>{let{tr:e}=t;return e.setSelection(n),!0}),e()})})}),tl=function(t){let e=!(arguments.length>1)||void 0===arguments[1]||arguments[1],o=(0,h.O2)("editor"),n=(0,u.GV)(e=>{let o=(0,W.wC)(e),n=(0,W.$i)(e);return o===J.w.SLIDE_VIEW&&n?(0,L.OB)(n):t});(0,i.useEffect)(()=>{e&&n&&o.setScrollSelector(n)},[e,o,n])},ts=t=>{let{onCreate:e=()=>{},extensions:o=[],readOnly:h=!0,shouldSupportComments:g=!1,shouldSupportMenus:y=!1,initialContent:x,doc:f,docId:b,isStatic:v=!1,animationsEnabled:E=!0,scrollingParentSelector:k,theme:w,editorId:j,isThumbnail:z}=t,_=(0,A.Iy)("edit",f),S=(null==f?void 0:f.organization)&&f.organization.id,[C]=(0,i.useState)(y),[I,F]=(0,i.useState)(!1),R=(0,l.wA)(),L=(0,u.GV)(W.Cl),J=(0,i.useMemo)(()=>(0,ta.C)(),[]),H=(0,r.I)(),$=(0,m.u)("debugLogging"),V=!!(null==f?void 0:f.site),{setEditorRendered:X,onEditorUnload:Y}=(0,q.f)(j);G(k),tl(k,!v);let Z=(0,O.hG)({async onCreate(t){let{editor:e}=t;console.debug("[EditorCore][onCreate] TipTap editor is now ready"),(0,tr.m)(e,R),F(!0)},onUpdate(t){var e,o;let{editor:n}=t;null===(e=U.U.getState(n.state))||void 0===e||e.processChanges(R),null===(o=B.Jl.getState(n.state))||void 0===o||o.processChanges(R)},onSelectionUpdate(t){let{editor:e}=t;console.debug("[EditorCore][onSelectionUpdate] selection updated",e.state.selection.from,e.state.selection.to,e.state.selection)},onDestroy(){$&&console.warn("[EditorCore][onDestroy] This should only happen on page navigation."),Y(),R((0,W.cL)()),R((0,c.qq)())},extensions:J.concat(o),content:x,editable:!1});z&&(Z.isThumbnail=!0),(0,p.GZ)(Z);let Q=!!(Z&&I);return((0,i.useEffect)(()=>{w&&R((0,W.Yl)({theme:w}))},[w,R]),(0,T.Vg)(()=>{Z&&Q&&(Z.commands.migrateNativeEmojis(),ti(Z).then(()=>{console.debug("[EditorCore][fullyReady] Menus initialized & focused at start - fully ready"),e({editor:Z}),requestAnimationFrame(()=>{requestIdleCallback(()=>{X()})})}))},[Z,Q,e,X],[Q]),(0,i.useEffect)(()=>{f&&R((0,W.BN)({doc:f}))},[f,R]),(0,i.useEffect)(()=>{R((0,W.Rs)({isStatic:v})),Z.isStatic=v},[Z,v,R]),(0,i.useEffect)(()=>{R((0,W.$j)({animationsEnabled:E&&!1===H}))},[E,H,R]),(0,i.useEffect)(()=>{R((0,W.yJ)({commentsEnabled:g}))},[g,R]),(0,i.useEffect)(()=>{if(Z)return s.$.DEBUG_ENABLED&&void 0===window.gammaEditorCore&&(window.gammaEditorCore=Z,window.contentToAIHTML=()=>{if(Z&&Z.state.doc.firstChild)return(0,d.K)(Z,Z.state.doc.firstChild.content)}),Z.gammaOrgId=S,Z.gammaDocId=b,Z.editorId=j,Z.multipageEnabled=V,()=>{delete window.gammaEditorCore}},[Z,b,S,j,V]),(0,i.useEffect)(()=>{Z&&R((0,W.kd)({isAllowedToEdit:_&&!h}))},[Z,R,h,_]),(0,i.useEffect)(()=>{Z&&Q&&requestAnimationFrame(()=>{Z.setOptions({editable:L})})},[Z,R,L,Q,X]),(0,M.gp)(Z),(0,M.l3)(Z),(0,M.eb)(Z,L),(0,M.WS)(Z),Z&&I)?(0,n.jsx)(P.Q,{value:j,children:(0,n.jsxs)(a.az,{id:"editor-core-root",className:"editor-core-root","data-testid":"editor-core-root",width:"100%",position:"relative",sx:N.q,children:[(0,n.jsx)(O.$Z,{editor:Z,style:{width:"100%",height:"100%"},"data-testid":"tiptap-react-root-wrapper",className:"highlight-mask"}),C&&(0,n.jsx)(D.A,{editorId:j,children:(0,n.jsx)(tc,{editor:Z,scrollingParentSelector:k})})]})}):(0,n.jsx)(n.Fragment,{})},td=t=>{let{isInsideDrawer:e,...o}=t;return(0,n.jsx)(te,{isInsideDrawer:e,docId:o.docId,children:(0,n.jsx)(ts,{...o})})},tc=(0,i.memo)(t=>{let{editor:e,scrollingParentSelector:o}=t;return(0,n.jsxs)(n.Fragment,{children:[(0,n.jsx)(R.Ht,{editor:e,scrollingParentSelector:o}),(0,n.jsx)(C,{editor:e}),(0,n.jsx)(F.G,{editor:e})]})})},23529:(t,e,o)=>{o.d(e,{m:()=>i});var n=o(69646),a=o(44428),r=o(69771);let i=(t,e)=>{var o,i;null===(o=r.U.getState(t.state))||void 0===o||o.compute(t.state.doc).processChanges(e),null===(i=a.Jl.getState(t.state))||void 0===i||i.compute(t.state).processChanges(e),t.commands.command(()=>((0,n.jL)(t.state.doc,e),!0))}},3365:(t,e,o)=>{o.d(e,{HC:()=>i,Ol:()=>s,f$:()=>d,k5:()=>l});var n=o(46108),a=o.n(n),r=o(8558);let i=function(t){let e=arguments.length>1&&void 0!==arguments[1]?arguments[1]:{docFlags:{}};return{type:"doc",content:[{type:"document",attrs:e,content:[{type:"card",content:[{type:"title",attrs:{level:r.f.DefaultTitle}},{type:"contributors"}]},{type:"card",content:[{type:"heading"}]}]}]}},l=function(){let t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{docFlags:{cardLayoutsEnabled:!0}};return{type:"doc",content:[{type:"document",attrs:t,content:[{type:"card",content:[{type:"title",attrs:{level:r.f.DefaultTitle}}]}]}]}},s=function(){let t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{docFlags:{cardLayoutsEnabled:!0}},e=arguments.length>1&&void 0!==arguments[1]?arguments[1]:[],o=a()(t);return o.docFlags=t.docFlags||{},o.docFlags.cardLayoutsEnabled=!0,{type:"doc",content:[{type:"document",attrs:o,content:e}]}},d=function(t){let e=arguments.length>1&&void 0!==arguments[1]?arguments[1]:{docFlags:{}};return{type:"doc",content:[{type:"document",attrs:e,content:[{type:"card",content:[{type:"title",attrs:{level:r.f.DefaultTitle}},{type:"contributors"}]},{type:"card",attrs:{previewContent:null,background:{type:"none"},container:{},cardSize:"default",layout:"blank"},content:[{type:"heading",attrs:{horizontalAlign:null,level:2},content:[{type:"text",text:"Quick Start Checklist"}]},{type:"todo",attrs:{indent:0,checked:!1,fontSize:null},content:[{type:"emoji",attrs:{id:"zap",native:"⚡"}},{type:"text",text:"️ "},{type:"text",marks:[{type:"bold"}],text:"Type "},{type:"text",marks:[{type:"code"}],text:"/"},{type:"text",marks:[{type:"bold"}],text:" in a card to open the slash menu"}]},{type:"bullet",attrs:{indent:1,fontSize:null},content:[{type:"text",text:"It lets you browse and add content blocks quickly"}]},{type:"bullet",attrs:{indent:1,fontSize:null},content:[{type:"text",text:"Content blocks can be added from the "},{type:"text",marks:[{type:"footnoteLabel",attrs:{noteId:"7jZEg"}}],text:"insert menu"},{type:"footnote",attrs:{noteId:"7jZEg"},content:[{type:"image",attrs:{id:"Y3FmX",horizontalAlign:null,src:"https://cdn.gamma.app/zc87vhr30n8uf3n/b98e59519a994c7abd900f44683bbb5d/optimized/insert-menu-explainer.gif",tempUrl:"blob:https://gamma.app/983566b4-f21e-4d28-bc69-d448c1976833",uploadStatus:0,meta:{width:700,height:459,date_file_modified:"2023/01/20 23:08:42 GMT",duration:4.42,aspect_ratio:1.525,has_clipping_path:!1,frame_count:36,colorspace:"RGB",average_color:"#f7f5f5"},providerMeta:null,name:"insert-menu-explainer.gif",query:null,source:"image.custom",showPlaceholder:!1,fullWidthBlock:!1,resize:{clipType:null,clipPath:null,clipAspectRatio:null,width:null}}}]},{type:"text",text:" if you prefer to drag"}]},{type:"todo",attrs:{indent:0,checked:!1,fontSize:null},content:[{type:"emoji",attrs:{id:"heavy_plus_sign",native:"➕"}},{type:"text",text:" "},{type:"text",marks:[{type:"bold"}],text:"Add a new card"},{type:"text",text:" (type "},{type:"text",marks:[{type:"code"}],text:"/card"},{type:"text",text:") or select a template via the "},{type:"text",marks:[{type:"footnoteLabel",attrs:{noteId:"xLt8J"}}],text:"plus button"},{type:"footnote",attrs:{noteId:"xLt8J"},content:[{type:"image",attrs:{id:"3CSXR",horizontalAlign:null,src:"https://cdn.gamma.app/zc87vhr30n8uf3n/a8d6b7149f75401ebd54e6b022670a3e/optimized/image.png",tempUrl:"blob:https://gamma.app/5368f25a-cf30-4410-bf62-bd26080b01f5",uploadStatus:0,meta:{width:844,height:608,date_file_modified:"2023/01/20 21:38:09 GMT",aspect_ratio:1.388,has_clipping_path:!1,frame_count:1,colorspace:"RGB",average_color:"#f7f5f5"},providerMeta:null,name:"image.png",query:null,source:"image.custom",showPlaceholder:!1,fullWidthBlock:!1,resize:{clipType:null,clipPath:null,clipAspectRatio:null,width:null}}}]}]},{type:"todo",attrs:{indent:0,checked:!1,fontSize:null},content:[{type:"emoji",attrs:{id:"newspaper",native:"\uD83D\uDCF0"}},{type:"text",text:" "},{type:"text",marks:[{type:"bold"}],text:"Create multi-column layouts"},{type:"text",text:" (type "},{type:"text",marks:[{type:"code"}],text:"/columns"},{type:"text",text:") or "},{type:"text",marks:[{type:"footnoteLabel",attrs:{noteId:"JyryA"}}],text:"drag blocks"},{type:"footnote",attrs:{noteId:"JyryA"},content:[{type:"image",attrs:{id:"iJXTh",horizontalAlign:null,src:"https://cdn.gamma.app/zc87vhr30n8uf3n/3e47c137ba5d4d13bdafda584b3a8f94/optimized/drag-columns-optimized.gif",tempUrl:"blob:https://gamma.app/654cd2e0-6972-4a24-a7c0-25eff0cf9050",uploadStatus:0,meta:{width:500,height:313,date_file_modified:"2023/01/20 21:35:04 GMT",duration:9.83,aspect_ratio:1.597,has_clipping_path:!1,frame_count:62,colorspace:"RGB",average_color:"#e8cac5"},providerMeta:null,name:"drag-columns-optimized.gif",query:null,source:"image.custom",showPlaceholder:!1,fullWidthBlock:!1,resize:{clipType:null,clipPath:null,clipAspectRatio:null,width:null}}}]},{type:"text",text:" side by side"}]},{type:"todo",attrs:{indent:0,checked:!1,fontSize:null},content:[{type:"emoji",attrs:{id:"bridge_at_night",native:"\uD83C\uDF09"}},{type:"text",text:" "},{type:"text",marks:[{type:"bold"}],text:"Add an image "},{type:"text",text:"(type "},{type:"text",marks:[{type:"code"}],text:"/image"},{type:"text",text:"). You can drag in or paste image files too!"}]},{type:"todo",attrs:{indent:0,checked:!1,fontSize:null},content:[{type:"emoji",attrs:{id:"art",native:"\uD83C\uDFA8"}},{type:"text",text:" "},{type:"text",marks:[{type:"bold"}],text:"Change the deck "},{type:"text",marks:[{type:"bold"},{type:"footnoteLabel",attrs:{noteId:"mTOBc"}}],text:"theme"},{type:"footnote",attrs:{noteId:"mTOBc"},content:[{type:"paragraph",attrs:{horizontalAlign:null,fontSize:null},content:[{type:"text",text:"Click the "},{type:"text",marks:[{type:"code"}],text:"Theme"},{type:"text",text:" button in the top toolbar, then try clicking some of the different options"}]},{type:"image",attrs:{id:"GHNdu",horizontalAlign:"center",src:"https://cdn.gamma.app/zc87vhr30n8uf3n/db839cee72b7497b9ff1424987641ed5/optimized/image.png",tempUrl:null,uploadStatus:0,meta:{width:806,height:1678,date_file_modified:"2022/08/01 23:13:09 GMT",aspect_ratio:.48,has_clipping_path:!1,frame_count:1,colorspace:"RGB",average_color:"#ccc7ca"},providerMeta:null,name:"image.png",query:null,source:"image.custom",showPlaceholder:!1,fullWidthBlock:!1,resize:{clipType:"inset",clipPath:["17.31441903150651%","0%","41.5572854690296%","0%"],clipAspectRatio:1.1678975712005124,width:209.77777777777777}}}]},{type:"text",text:" to give it a different look and feel"}]},{type:"todo",attrs:{indent:0,checked:!1,fontSize:null},content:[{type:"emoji",attrs:{id:"arrow_forward",native:"▶️"}},{type:"text",text:" "},{type:"text",marks:[{type:"bold"}],text:"Try present mode"},{type:"text",text:" and use your arrow keys and "},{type:"text",marks:[{type:"footnoteLabel",attrs:{noteId:"UGnAU"}}],text:"spotlight"},{type:"footnote",attrs:{noteId:"UGnAU"},content:[{type:"paragraph",attrs:{horizontalAlign:null,fontSize:null},content:[{type:"text",text:"Spotlight lets you"},{type:"text",marks:[{type:"bold"}],text:" focus "},{type:"text",text:"on"},{type:"text",marks:[{type:"bold"}],text:" one piece of content at a time. "},{type:"text",text:"While presenting, press "},{type:"text",marks:[{type:"code"}],text:"s"},{type:"text",text:" to enter spotlight mode."}]},{type:"image",attrs:{id:"YOFhU",horizontalAlign:null,src:"https://cdn.gamma.app/zc87vhr30n8uf3n/ed0b362f6c2f4a438cd3188ba7697a02/optimized/spotlight-compressed.gif",tempUrl:"blob:https://gamma.app/d2eaae7f-515e-4e06-b396-4b0551a5a999",uploadStatus:0,meta:{width:1200,height:750,date_file_modified:"2023/01/13 21:48:00 GMT",duration:6.66,aspect_ratio:1.6,has_clipping_path:!1,frame_count:34,colorspace:"RGB",average_color:"#323030"},providerMeta:null,name:"spotlight-compressed.gif",query:null,source:"image.custom",showPlaceholder:!1,fullWidthBlock:!1,resize:{clipType:null,clipPath:null,clipAspectRatio:null,width:null}}}]},{type:"text",text:" to move around"}]},{type:"todo",attrs:{indent:0,checked:!1,fontSize:null},content:[{type:"emoji",attrs:{id:"tada",native:"\uD83C\uDF89"}},{type:"text",text:" "},{type:"text",marks:[{type:"bold"}],text:"Share your deck"},{type:"text",text:"."},{type:"text",marks:[{type:"bold"}],text:" "},{type:"text",text:"Publish it to the world, invite others, or export a PDF"}]}]}]}]}}}}]);
//# sourceMappingURL=6377-1d26d0b150c4486a.js.map