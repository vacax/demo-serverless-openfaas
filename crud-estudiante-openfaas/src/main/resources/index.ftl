<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <!-- Required Stylesheets -->
    <link
            type="text/css"
            rel="stylesheet"
            href="https://unpkg.com/bootstrap/dist/css/bootstrap.min.css"
    />
    <link
            type="text/css"
            rel="stylesheet"
            href="https://unpkg.com/bootstrap-vue@latest/dist/bootstrap-vue.css"
    />

    <!-- Required scripts -->
    <script src="https://unpkg.com/vue"></script>
    <script src="https://unpkg.com/babel-polyfill@latest/dist/polyfill.min.js"></script>
    <script src="https://unpkg.com/bootstrap-vue@latest/dist/bootstrap-vue.js"></script>
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>

    <title>Ejemplo CRUD con VueJS</title>
</head>
<body>

<div class="container" id="app">
    <div class="row mt-3">
        <div class="col-sm">
            <h1 class="text-center">Ejemplo CRUD Sobre OpenFaaS</h1>
            <div>
                <b-button variant="outline-primary" v-b-modal.modal-1 @click="nuevoEstudiante">Crear Estudiante</b-button>
            </div>
        </div>
    </div>
    <div class="row mt-3">
        <div class="col-sm">
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Matrícula</th>
                    <th scope="col">Nombre</th>
                    <th scope="col">Correo</th>
                    <th scope="col">Acciones</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="estudiante in estudiantes">
                    <td>{{estudiante.matricula}}</td>
                    <td>{{estudiante.nombre}}</td>
                    <td>{{estudiante.carrera}}</td>
                    <td>
                        <button class="btn btn-secondary" @click="editarEstudiante(estudiante)">Editar</button> |
                        <button class="btn btn-danger" @click="eliminarEstudiante(estudiante)">Eliminar</button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>

    <b-modal id="modal-1" hide-footer :title="titulo">
        <b-form @submit.prevent="crearActualizarEstudiante" @reset="limpiar" >

            <!-- Matricula       -->
            <b-form-group
                    label="Matrícula"
                    label-for="g1">
                <b-form-input
                        id = "g1"
                        v-model="nuevo.matricula"
                        type="number"
                        required
                        placeholder="Matrícula"
                ></b-form-input>
            </b-form-group>

            <!-- Nombre            -->
            <b-form-group
                    label="Nombre:"
                    label-for="g3">
                <b-form-input
                        id = "g3"
                        v-model="nuevo.nombre"
                        type="text"
                        required
                        placeholder="Nombre"
                ></b-form-input>
            </b-form-group>

            <!-- Carrera            -->
            <b-form-group
                    label="Carrera:"
                    label-for="g3">
                <b-form-input
                        id = "g3"
                        v-model="nuevo.carrera"
                        type="text"
                        required
                        placeholder="Carrera"
                ></b-form-input>
            </b-form-group>
            <b-button type="submit" variant="primary">Enviar</b-button>
            <b-button type="reset" variant="danger">Limpiar</b-button>
        </b-form>
    </b-modal>
</div>

<!-- Libreria Vuejs -->
<script>

    var URL_API = "/function";

    var app = new Vue({

        el: '#app',

        data: {
            titulo: "Creación de Estudiante",
            editando: false,
            nuevo: {
                matricula: '',
                nombre: '',
                carrera: ''
            },
            estudiantes: []
        } ,

        created: function () {//representa la función de inicialización Vuejs

            console.log("Inicializando le proyecto en Vuejs: ", this.titulo);
        },

        mounted: function () {

            this.$nextTick(function () {  //garantizo que todos los elementos están renderizados.
                console.log("Cargando todo el render del proyecto");
                this.listaEstudiantes();
            });
        },

        methods: {

            listaEstudiantes: function(){
                console.log("Cargando lista de Estudiante");
                axios.get(URL_API+'/listar-estudiante-openfaas').then(response => {
                    //
                    console.log("Recuperando información del servidor");
                //
                this.estudiantes = response.data;
            }).catch(error => { console.log(error); });
            },

            nuevoEstudiante: function(){
                console.log("Nuevo Estudiante");
                this.titulo = "Nuevo Estudiante";
                this.editando = false;
                this.limpiar();
            },

            crearActualizarEstudiante: function(){
                console.log("datos para almacenar: ", this.nuevo);
                if(!this.editando){
                    //creando
                    axios.post(URL_API+'/crear-estudiante-openfaas', this.nuevo).then(response => {
                        this.limpiar();
                    this.listaEstudiantes();
                }).catch(error => console.log(error));
                } else{
                    //editando
                    axios.post(URL_API+'/actualizar-estudiante-openfaas', this.nuevo).then(response => {
                        this.limpiar();
                    this.listaEstudiantes();
                }).catch(error => console.log(error));
                }

                this.$bvModal.hide('modal-1');
            },

            editarEstudiante: function (estudiante) {
                console.log("El estudiante seleccionado para actualizar: ", estudiante);
                this.titulo = "Editando Estudiante - "+estudiante.matricula;
                this.nuevo = Object.assign({}, estudiante); //copiando el objeto, evitando el cambio directo en el form.
                this.editando = true;
                this.$bvModal.show('modal-1');
            },

            eliminarEstudiante: function (estudiante) {
                console.log("Eliminando la matricula: ", estudiante);
                axios.post(URL_API+'/eliminar-estudiante-openfaas', estudiante).then(response => {
                    this.listaEstudiantes();
            }).catch(error => console.log(error));
            },

            limpiar: function () {
                this.nuevo.matricula='';
                this.nuevo.nombre='';
                this.nuevo.carrera='';
            }

        }
    });
</script>
</body>
</html>