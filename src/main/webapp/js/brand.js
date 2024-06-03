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

        editDialogVisible: false,
        dialogVisible: false,
        multipleSelection: [],
        tableData: [],
        currentPage: 1,
        totalCount: 0,
        pageSize: 10
    },

    methods: {
        handleMultiDelete() {
            var _this = this;

            if (this.multipleSelection.length === 0) {
                this.$message.warning('您未选择任何数据');
                setTimeout(() => {
                    _this.$message.closeAll();
                }, 2000);
                return;
            }

            _this.$confirm('此操作将永久删除所选的所有记录, 是否继续?', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                axios({
                    method: "POST",
                    url: "http://localhost:8080/zd_project_war/brands/deleteBatch",
                    data: _this.multipleSelection
                }).then(function (resp) {
                    if (resp.data.code == 30061) {
                        _this.selectByPageAndCondition();
                        _this.$message({
                            message: '恭喜你，删除成功',
                            type: 'success'
                        });
                    }

                    if (resp.data.code == 30060) {
                        _this.$message.error('发生错误，请重新提交');
                    }
                })
            }).catch(() => {
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
                axios({
                    method: "POST",
                    url: "http://localhost:8080/zd_project_war/brands/delete",
                    data: row
                }).then(function (resp) {
                    if (resp.data.code == 30061) {
                        _this.selectByPageAndCondition();
                        _this.$message({
                            message: '恭喜你，删除成功',
                            type: 'success'
                        });
                    }

                    if (resp.data.code == 30060) {
                        _this.$message.error('发生错误，请重新提交');
                    }
                })
            }).catch(() => {
                _this.$message({
                    message: '已取消删除',
                    type: 'info'
                });
            });
        },

        selectByPageAndCondition() {
            var _this = this;
            axios({
                method: "GET",
                url: "http://localhost:8080/zd_project_war/brands/pages/" + _this.currentPage + "/pageSize/"
                    + _this.pageSize + "/brand?brandName=" + _this.searchBrand.brandName
                    + "&companyName=" + _this.searchBrand.companyName + "&status=" + _this.searchBrand.status
            }).then(function (resp) {
                _this.tableData = resp.data.data.row;
                _this.totalCount = resp.data.data.totalCount;
            })
        },

        onSubmit() {
            this.selectByPageAndCondition();
        },

        resetBrand() {
            this.brand = {
                status: '',
                brandName: '',
                companyName: '',
                id: '',
                ordered: '',
                description: ''
            };
        },

        // TODO 需判断插入的ordered必须得是数字
        addBrand() {
            var _this = this;
            axios({
                method: "POST",
                url: "http://localhost:8080/zd_project_war/brands",
                data: _this.brand
            }).then(function (resp) {
                if (resp.data.code == 30041) {
                    _this.dialogVisible = false;
                    _this.selectByPageAndCondition();
                    _this.$message({
                        message: '恭喜你，添加成功',
                        type: 'success'
                    });
                }

                if (resp.data.code == 30040) {
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

        handleEdit(row) {
            this.modifyBrand = row;
            this.editDialogVisible = true;
        },

        // TODO 需判断插入的ordered必须得是数字
        handleUpdate() {
            var _this = this;
            axios({
                method: "POST",
                url: "http://localhost:8080/zd_project_war/brands/update",
                data: _this.modifyBrand
            }).then(function (resp) {
                if (resp.data.code == 30051) {
                    _this.editDialogVisible = false;
                    _this.selectByPageAndCondition();
                    _this.$message({
                        message: '恭喜你，修改成功',
                        type: 'success'
                    });
                }

                if (resp.data.code == 30050) {
                    _this.$message.error('发生错误，请重新提交');
                }
            })
        }
    }
})
