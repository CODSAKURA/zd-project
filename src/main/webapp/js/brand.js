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
        pageSize: 10,
        rules: {
            brandName: [
                { required: true, message: '请输入品牌名称', trigger: 'blur' }
            ],
            companyName: [
                { required: true, message: '请输入企业名称', trigger: 'blur' }
            ],
            ordered: [
                { required: true, message: '请输入排序', trigger: 'blur' },
                { pattern: /^[0-9]*$/, message: '排序只能包含数字', trigger: 'blur' }
            ]
        }
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

        addBrand() {
            var _this = this;
            this.$refs['AddBrandForm'].validate((valid) => {
                if (valid) {
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
                    });
                } else {
                    _this.$message.error('表单验证失败，请检查输入内容');
                }
            });
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

        // TODO 有bug，点取消时需删除之前添加的
        handleUpdate() {
            var _this = this;
            this.$refs['ModifyBrandForm'].validate((valid) => {
                if (valid) {
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
                    });
                } else {
                    _this.$message.error('表单验证失败，请检查输入内容');
                }
            });
        }
    }
})
