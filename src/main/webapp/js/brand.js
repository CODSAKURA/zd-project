new Vue({
    el: "#app",

    mounted() {
        this.selectByPageAndCondition();
    },

    data: {
        brand: {
            status: '',
            brandName: '',
            companyName: '',
            id: '',
            ordered: '',
            description: ''
        },

        searchBrand: {
            status: '',
            brandName: '',
            companyName: '',
            id: '',
            ordered: '',
            description: ''
        },

        modifyBrand: {
            status: '',
            brandName: '',
            companyName: '',
            id: '',
            ordered: '',
            description: ''
        },

        editDialogVisible:false,
        dialogVisible: false, // TODO 新增按钮之后数据还是之前填的数据，需要重新初始化
        multipleSelection: [],
        tableData: [],
        currentPage: 1,
        selectedIds:[],
        totalCount:0,//总记录数
        pageSize:10 //每页显示的条数
    },

    methods: {
        handleMultiDelete() {
            var _this = this;

            // 检查是否有选中的数据
            if (this.multipleSelection.length === 0) {
                this.$message.warning('您未选择任何数据');
                setTimeout(() => {
                    _this.$message.closeAll();
                }, 2000);
                return; // 不执行删除操作
            }

            _this.$confirm('此操作将永久删除所选的所有记录, 是否继续?', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                // 用户点击确定后的操作【从multipleSelection中提取id，并存在selectedIds】
                for (let i = 0; i < this.multipleSelection.length; i++) {
                    let selectionElement = this.multipleSelection[i];
                    this.selectedIds[i] = selectionElement.id;
                }

                // 通过ajax提交数据并执行方法
                axios({
                    method: "POST",
                    url: "http://localhost:8080/zd_project_war/brand/deleteByIds",
                    data: _this.selectedIds
                }).then(function (resp) {
                    if (resp.data == "success") {
                        _this.selectByPageAndCondition();
                        _this.$message({
                            message: '恭喜你，删除成功',
                            type: 'success'
                        });
                    } else {
                        _this.$message.error('发生错误，请重新提交');
                    }
                })
            }).catch(() => {
                //用户点击取消后的操作
                _this.$message({
                    message: '已取消删除',
                    type: 'info'
                });
            });
        },

        handleDeleteConfirmation(row) {
            var _this = this;
            _this.$confirm('此操作将永久删除该记录, 是否继续?', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                // 用户点击确定后的操作
                axios({
                    method: "POST",
                    url: "http://localhost:8080/zd_project_war/brand/delete",
                    data: row
                }).then(function (resp) {
                    if (resp.data == "success") {
                        _this.selectByPageAndCondition();
                        _this.$message({
                            message: '恭喜你，删除成功',
                            type: 'success'
                        });
                    } else {
                        _this.$message.error('发生错误，请重新提交');
                    }
                })
            }).catch(() => {
                // 用户点击取消后的操作
                _this.$message({
                    message: '已取消删除',
                    type: 'info'
                });
            });
        },

        selectByPageAndCondition() {
            var _this = this;
            axios({
                method: "post",
                url: "http://localhost:8080/zd_project_war/brand/selectByPageAndCondition?currentPage="+_this.currentPage+"&pageSize="+_this.pageSize,
                data:_this.searchBrand
            }).then(function (resp) {
                _this.tableData = resp.data.row; //resp.data=[row:{}, totalCount:10000]
                _this.totalCount = resp.data.totalCount;
            })
        },

        onSubmit() {
            this.selectByPageAndCondition();
        },

        addBrand() {
            var _this = this;
            axios({
                method: "POST",
                url: "http://localhost:8080/zd_project_war/brand/add",
                data: _this.brand
            }).then(function (resp) {
                if (resp.data == "success") {
                    _this.dialogVisible = false;
                    _this.selectByPageAndCondition();
                    _this.$message({
                        message: '恭喜你，添加成功',
                        type: 'success'
                    });
                } else {
                    _this.$message.error('发生错误，请重新提交');
                }
            })
        },

        handleSelectionChange(val) {
            this.multipleSelection = val;
        },

        handleSizeChange(val) {
            this.pageSize = val;
            this.selectByPageAndCondition();
        },

        handleCurrentChange(val) {
            this.currentPage = val;
            this.selectByPageAndCondition();
        },

        handleEdit(row){
            this.modifyBrand = row;
            this.editDialogVisible = true;
        },

        handleUpdate(){
            var _this = this;
            axios({
                method: "POST",
                url: "http://localhost:8080/zd_project_war/brand/update",
                data: _this.modifyBrand
            }).then(function (resp) {
                if (resp.data == "success") {
                    _this.editDialogVisible = false;
                    _this.selectByPageAndCondition();
                    _this.$message({
                        message: '恭喜你，修改成功',
                        type: 'success'
                    });
                } else {
                    _this.$message.error('发生错误，请重新提交');
                }
            })
        }
    }
})