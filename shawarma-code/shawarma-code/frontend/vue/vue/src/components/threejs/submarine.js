import * as THREE from 'three';
import {GLTFLoader} from "three/examples/jsm/loaders/GLTFLoader"
import { DRACOLoader } from 'three/examples/jsm/loaders/DRACOLoader';
import {OrbitControls} from "three/examples/jsm/controls/OrbitControls";
import {CSS2DRenderer,CSS2DObject} from "three/examples/jsm/renderers/CSS2DRenderer";


//初始化场景
const scene=new THREE.Scene();
scene.background = new THREE.Color("0xffffff");
const camera=new THREE.PerspectiveCamera(75,window.innerWidth/window.innerHeight,0.1,2000);//透视相机
camera.position.set(5,5,13);//设置摄像头位置
camera.aspect=window.innerWidth/window.innerHeight;//设置摄像头宽高比
camera.updateProjectionMatrix();//更新摄像头投影矩阵
scene.add(camera);


const renderer=new THREE.WebGLRenderer({
  antialias:true,//抗锯齿

});//初始化渲染器
renderer.outputEncoding=THREE.sRGBEncoding;
renderer.setSize(window.innerWidth,window.innerHeight);//设置宽高
renderer.toneMapping=THREE.ACESFilmicToneMapping;
renderer.toneMappingExposure=0.5;

window.addEventListener("revise",()=>{
  camera.aspect=window.innerWidth/window.innerHeight;
  camera.updateProjectionMatrix();
  renderer.setSize(window.innerWidth,window.innerHeight);
});//监听屏幕大小修改宽高比例

const controls=new OrbitControls(camera,renderer.domElement);//实例化控制器

const comDiv = document.createElement('button');
comDiv.className = 'label';
comDiv.innerHTML="传感器数据";
const comLabel = new CSS2DObject(comDiv);
comLabel.position.set(0,0.2,0);

const gpsDiv = document.createElement('button');
gpsDiv.className = 'label';
gpsDiv.innerHTML = "gps数据";
const gpsLabel = new CSS2DObject(gpsDiv);

const dracoLoader= new DRACOLoader();
dracoLoader.setDecoderPath("/draco/");
const gltfLoader=new GLTFLoader();
gltfLoader.setDRACOLoader(dracoLoader);
const group=new THREE.Group();
const computergroup=new THREE.Group();
const gpsgroup=new THREE.Group();
const subgroup=new THREE.Group();

//创建加载指示器
const loadingIndicatorGeometry = new THREE.BoxGeometry(1, 1);
const loadingIndicatorMaterial = new THREE.MeshBasicMaterial({
    color: 0x808080, // 灰色，表示加载中
    transparent: true,
    opacity: 0.8,
});
const loadingIndicator = new THREE.Mesh(loadingIndicatorGeometry, loadingIndicatorMaterial);
scene.add(loadingIndicator);
// 加载指示器的旋转动画
function animateLoadingIndicator() {
    loadingIndicator.rotation.z += 0.05; // 每帧旋转5度
    loadingIndicator.rotation.y += 0.05; // 每帧旋转5度
    loadingIndicator.rotation.x += 0.05; // 每帧旋转5度
    renderer.render(scene, camera);
    requestAnimationFrame(animateLoadingIndicator); // 请求下一帧
}

// 开始旋转加载指示器
animateLoadingIndicator();

gltfLoader.load( '/model/submarine/submarine.gltf', function ( gltf ) {
      const model=gltf.scene;
      const computer=gltf.scene.getObjectByName("computer");
      const gps=gltf.scene.getObjectByName("gps");
      const submarine=gltf.scene.getObjectByName("submarine001");
      computergroup.add(computer);
      subgroup.add(submarine);
      gpsgroup.add(gps);
      group.add(computergroup,gpsgroup,subgroup);
      model.scale.set(0.9,0.9,0.9);
      scene.add(group);
    // 移除加载指示器
    scene.remove(loadingIndicator);
    // 停止渲染加载指示器
    cancelAnimationFrame(animateLoadingIndicator);
    renderer.domElement.addEventListener('click', function (event) {
         // .offsetY、.offsetX以canvas画布左上角为坐标原点,单位px
         const px = event.offsetX;
         const py = event.offsetY;
         //屏幕坐标px、py转WebGL标准设备坐标x、y
         //width、height表示canvas画布宽高度
         const x = (px / window.innerWidth) * 2 - 1;
         const y = -(py /window.innerHeight) * 2 + 1;
         //创建一个射线投射器`Raycaster`
         const raycaster = new THREE.Raycaster();
         //.setFromCamera()计算射线投射器`Raycaster`的射线属性.ray
         // 形象点说就是在点击位置创建一条射线，射线穿过的模型代表选中
         raycaster.setFromCamera(new THREE.Vector2(x, y), camera);
         //对参数中的网格模型对象进行射线交叉计算
          const intersects2 = raycaster.intersectObjects(gps.children);
          const intersects3 = raycaster.intersectObjects(computer.children);
         // intersects.length大于0说明，说明选中了模型
          if (intersects2.length > 0) {
              intersects2[0].object.add(gpsLabel);
          }
          if (intersects3.length > 0) {
              intersects3[0].object.add(comLabel);
          }
     })
  });
fetch('/model/submarine/submarine.gltf')
    .then(response => response.blob())
    .then(blob => {
        const url = URL.createObjectURL(blob);
        gltfLoader.load(url, (gltf) => {
            const model = gltf.scene;
            scene.add(model);
            // 清理对象URL
            URL.revokeObjectURL(url);
        });
    })
    .catch(error => {
        console.error('Error loading model:', error);
    });

var directionalLight = new THREE.DirectionalLight(0xffeedd,4.0);
   directionalLight.position.set(0, 10, 0);
   scene.add(directionalLight);
var directionalLightb = new THREE.DirectionalLight(0xffeedd,4.0);
   directionalLightb.position.set(0, -10, 0);
   scene.add(directionalLightb);
var directionalLightc = new THREE.DirectionalLight(0xffeedd,4.0);
   directionalLightc.position.set(0, 0, 10);
   scene.add(directionalLightc);
var directionalLightd = new THREE.DirectionalLight(0xffeedd,4.0);
   directionalLightd.position.set(0, 0, -5);
   scene.add(directionalLightd);



const labelRenderer = new CSS2DRenderer()
        // 设置渲染器宽高
labelRenderer.setSize(window.innerWidth,innerHeight);
        //设置2d渲染器布局
labelRenderer.domElement.style.position = 'absolute';
labelRenderer.domElement.style.top = '0px';
labelRenderer.domElement.style.left = '0px';
labelRenderer.domElement.style.zIndex = '0';
labelRenderer.domElement.style.pointerEvents = 'none';


function render(){
  renderer.render(scene,camera);//渲染场景
  labelRenderer.render(scene,camera);
  requestAnimationFrame(render);//自动更新渲染器
}
render();






export{renderer};
export{labelRenderer};
export{group};
export{comDiv};
