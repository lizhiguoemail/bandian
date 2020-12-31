===========================${table.entityName?uncap_first}/html/detail.component.html 开始1==============================
<div class="modal-header">
    <div class="modal-title" backdrop="static">详情</div>
</div>
<div class="modal-body">
    <nz-descriptions [nzBordered]="true" [nzColumn]="2">
<#list table.fields as field>
<#--<#if !field.keyFlag && field.name != 'creation_time' && field.name != 'creator_id' && field.name != 'last_modification_time' && field.name != 'last_modifier_id' && field.name != 'is_deleted' && field.name != 'version'>-->
    <#if !field.keyFlag >
        <#if field.columnType == 'BOOLEAN'>
        <nz-descriptions-item nzTitle="${field.comment}"><i *ngIf="model.${field.propertyName}" nz-icon="" nzType="check"></i><i
                    *ngIf="!(model.${field.propertyName})" nz-icon="" nzType="close"></i></nz-descriptions-item>
        <#else>
        <nz-descriptions-item nzTitle="${field.comment}" [nzSpan]="2" >{{model.${field.propertyName}}}</nz-descriptions-item>
        </#if>
    </#if>
</#list>
    </nz-descriptions>
</div>
=========================== 结束1==============================

===========================${table.entityName?uncap_first}/html/edit.component.html 开始2==============================
<form nz-form #form="ngForm" autocomplete="off" [nzLayout]="'vertical'">
    <div class="modal-header">
        <div class="modal-title" backdrop="static">编辑</div>
    </div>
    <nz-spin *ngIf="!model" class="modal-spin"></nz-spin>
    <div class="modal-body">
        <nz-row [nzGutter]="16">
<#list table.fields as field>
<#if !field.keyFlag >
        <nz-col nzLg="12" nzMd="12" nzSm="24">
        <#if field.columnType == 'INTEGER'>
        <x-number-textbox name="${field.propertyName}" label="${field.comment}" [required]="true" requiredMessage="${field.comment}不能为空" placeholder="请输入${field.comment}"
                          [(model)]="model.${field.propertyName}"></x-number-textbox>
        <#elseif field.columnType == 'LOCAL_DATE_TIME'>
        <x-date-picker rawId="${field.propertyName}" name="${field.propertyName}" label="${field.comment}" [required]="true" placeholder="请输入${field.comment}"
                       requiredMessage="${field.comment}不能为空"
                       [(model)]="model.${field.propertyName}"></x-date-picker>
        <#elseif field.columnType == 'BOOLEAN'>
            <x-radio name="${field.propertyName}" label="${field.comment}" [(model)]="model.${field.propertyName}" [dataSource]="[
            { text: '真', value: true },
            { text: '假', value: false }
             ]">
            </x-radio>

        <#elseif field.columnType == 'STRING' && field.type?contains("varchar") && ((field.type?substring(field.type?index_of('(')+1,field.type?index_of(')')))?number >= 500)>
            <x-textarea name="${field.propertyName}" label="${field.comment}" requiredMessage="${field.comment}不能为空" [(model)]="model.${field.propertyName}" [maxLength]="200"
                           [required]="true"></x-textarea>
        <#else>
            <x-textbox rawId="${field.propertyName}" name="${field.propertyName}" label="${field.comment}" requiredMessage="${field.comment}不能为空" [(model)]="model.${field.propertyName}" [maxLength]="200"
                       [required]="true"></x-textbox>
        </#if>
        </nz-col>
</#if>
</#list>
    </nz-row>
    </div>

    <div class="modal-footer">
        <x-button (onClick)="close()" class="mr-sm" text="取消"></x-button>
        <x-button #btnSubmit (onClick)="submit(form,btnSubmit)" color="primary" [validateForm]="true"></x-button>
    </div>

</form>
=========================== 结束2==============================

=========================== ${table.entityName?uncap_first}/html/index.component.html3==============================
<full-content>
    <nz-card [nzBordered]="false">
        <form autocomplete="off" class="search__form" nz-form nzLayout="inline">
            <nz-row [nzGutter]="16">
                <#list table.fields as field>
                <#if field_index == 1 >
                <nz-col nzXs="24" nzSm="24" nzMd="12" nzLg="8" nzXl="6">
                    <x-textbox rawId="${field.propertyName}" name="${field.propertyName}" label="${field.comment}" [(model)]="queryParam.${field.propertyName}"></x-textbox>
                </nz-col>
                </#if>
                </#list>
                <nz-col nzXs="24" nzSm="24" nzMd="12" nzLg="8" nzXl="6">
                    <nz-form-item>
                        <x-button #btnQuery (onClick)="query(btnQuery)" color="primary" text="查询">
                            <ng-template><i nz-icon nzType="search"></i></ng-template>
                        </x-button>
                        <x-button #btnRefresh (onClick)="refresh(btnRefresh)" class="mx-sm" text="重置">
                            <ng-template><i nz-icon nzType="undo"></i></ng-template>
                        </x-button>
                        <a class="expand" (click)="expand=!expand">
                            {{expand? '收起' : '展开'}} <i nz-icon [nzType]="expand ? 'up' : 'down'"></i>
                        </a>
                    </nz-form-item>
                </nz-col>

            </nz-row>
        </form>
    </nz-card>
    <nz-card nzTitle="列表内容" [nzBordered]="false" [nzExtra]="operatorActionTpl"
             [nzBodyStyle]="{'padding-top':'0px'}">
        <ng-template #operatorActionTpl>
            <x-button (onClick)="openCreateDialog()" class="mr-sm" color="primary" text="新建">
                <ng-template><i nz-icon nzType="plus"></i></ng-template>
            </x-button>
            <ng-container *ngIf="getCheckedLength() > 0">
                <button nz-button nz-dropdown [nzDropdownMenu]="batchMenu" nzPlacement="bottomLeft">
                    批量操作
                    <i nz-icon nzType="down"></i>
                </button>
                <nz-dropdown-menu #batchMenu="nzDropdownMenu">
                    <ul nz-menu>
                        <li nz-menu-item (click)="deleteBatch()">删除</li>
                    </ul>
                </nz-dropdown-menu>
            </ng-container>
            <nz-divider nzType="vertical"></nz-divider>
            <span [nzDropdownMenu]="menu" nz-tooltip nzTooltipTitle="密度" nz-dropdown>
                <i nz-icon class="toolbar-icon" nzType="column-height"></i>
            </span>
            <nz-dropdown-menu #menu="nzDropdownMenu">
                <ul nz-menu nzSelectable>
                    <li nz-menu-item (click)="setSise('d')">默认</li>
                    <li nz-menu-item (click)="setSise('m')">中等</li>
                    <li nz-menu-item (click)="setSise('s')">紧凑</li>
                </ul>
            </nz-dropdown-menu>
            <span full-toggle nz-tooltip nzTooltipTitle="全屏">
                <i class="toolbar-icon" nz-icon nzType="fullscreen"></i>
            </span>
            <span #btnRefresh (click)="refresh(btnRefresh)" nz-tooltip nzTooltipTitle="刷新">
                <i class="toolbar-icon" nz-icon nzType="reload"></i>
            </span>
        </ng-template>
        <nz-alert class="my-md" nzType="info" [nzMessage]="selectedRowsTpl" [nzShowIcon]="true">
            <ng-template #selectedRowsTpl>
                已选择<strong class="text-primary mx-md">{{getCheckedLength()}}</strong>行
                <a *ngIf="getCheckedLength() > 0" (click)="clearCheckboxs()"
                   class="ant-alert-close-icon ng-tns-c8-2 ng-star-inserted">清空</a>
            </ng-template>
        </nz-alert>
        <nz-table-wrapper #tableWrapper baseUrl="<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>" sortKey="SortId" [(queryParam)]="queryParam">
            <nz-table #table (nzPageIndexChange)="tableWrapper.pageIndexChange($event)" style="padding-top: 0;"
                      (nzPageSizeChange)="tableWrapper.pageSizeChange($event)" [(nzPageIndex)]="tableWrapper.queryParam.page"
                      [(nzPageSize)]="tableWrapper.queryParam.pageSize" [nzData]="tableWrapper.dataSource"
                      [nzFrontPagination]="false" [nzLoading]="tableWrapper.loading" [nzSize]="tableWrapper.tableSize"
                      [nzShowPagination]="tableWrapper.showPagination" [nzShowQuickJumper]="true" [nzShowSizeChanger]="true"
                      [nzShowTotal]="tableTotalDescriptionTpl" [nzTotal]="tableWrapper.totalCount">
                <thead (nzSortChange)="tableWrapper.sort($event)">
                <tr>
                    <th (nzCheckedChange)="tableWrapper.masterToggle()" nzWidth="30px"
                        [nzChecked]="tableWrapper.isMasterChecked()" [nzDisabled]="!tableWrapper.dataSource.length"
                        [nzIndeterminate]="tableWrapper.isMasterIndeterminate()"
                        [nzShowCheckbox]="tableWrapper.multiple">
                        {{tableWrapper.multiple?'':'单选'}}</th>
                    <th nzWidth="50px">#</th>
                <#list table.fields as field>
                <#if !field.keyFlag >
                    <th [nzSortFn]="true" nzColumnKey="${field.propertyName}">${field.comment}</th>
                </#if>
                </#list>
                    <th nzWidth="200px">操作</th>
                </tr>
                </thead>
                <tbody>
                <tr *ngFor="let row of table.data">
                    <td (click)="$event.stopPropagation()"
                        (nzCheckedChange)="tableWrapper.checkedSelection.toggle(row)"
                        [nzChecked]="tableWrapper.checkedSelection.isSelected(row)"
                        [nzShowCheckbox]="tableWrapper.multiple"><label (click)="$event.stopPropagation()"
                                                                        (ngModelChange)="tableWrapper.checkRowOnly(row)" *ngIf="!tableWrapper.multiple"
                                                                        name="radio_table" nz-radio
                                                                        [ngModel]="tableWrapper.checkedSelection.isSelected(row)"></label></td>
                    <td>{{row.lineNumber}}</td>
                    <#list table.fields as field>
                    <#if !field.keyFlag >
                        <#if field.columnType == 'BOOLEAN'>
                            <td>
                                <nz-tag *ngIf="row.${field.propertyName}" nzColor="green">
                                    真
                                </nz-tag>
                                <nz-tag *ngIf="!row.${field.propertyName}" nzColor="red">
                                    假
                                </nz-tag>
                            </td>
                        <#else>
                    <td> {{row.${field.propertyName}}}</td>
                        </#if>
                    </#if>
                    </#list>

                    <td>
                        <a (click)="openEditDialog(row)" nz-tooltip nzTooltipTitle="编辑数据" class="mr-sm"><i nz-icon
                                                                                                           nzType="edit" nzTheme="outline"></i> 编辑</a>
                        <a nz-popconfirm nzPopconfirmTitle="确定执行此操作吗?" nzOkText="确定" nzCancelText="取消"
                           (nzOnConfirm)="delete(null,row.id)" nz-tooltip nzTooltipTitle="删除数据" class="mr-sm"><i nz-icon
                                                                                                                 nzType="delete" nzTheme="outline"></i> 删除</a>
                        <a (click)="openDetailDialog(row)" nz-tooltip nzTooltipTitle="数据详情" class="mr-sm"><i nz-icon
                                                                                                             nzType="eye" nzTheme="outline"></i> 详情</a>
                    </td>
                </tr>
                </tbody>
            </nz-table>
            <ng-template #tableTotalDescriptionTpl let-range="range" let-total>{{ range[0] }}-{{ range[1] }} 共
                {{ total }} 条</ng-template>
        </nz-table-wrapper>
    </nz-card>
</full-content>
=========================== 结束3==============================

=========================== ${table.entityName?uncap_first}/model/${table.entityName?uncap_first}-query.ts 开始4==============================
import { QueryParameter } from '@utils';

/**
 * 查询参数
 */
export class ${table.entityName}Query extends QueryParameter {
<#list table.fields as field>
    /**
     * ${field.comment}
     */
    ${field.propertyName};
</#list>
<#list table.commonFields as field>
    /**
     * ${field.comment}
     */
    ${field.propertyName};
</#list>
}
=========================== 结束4==============================

=========================== ${table.entityName?uncap_first}/model/${table.entityName?uncap_first}-view-model.ts 开始5==============================
import { ViewModel } from '@utils';

/**
 * 参数
 */
export class ${table.entityName}ViewModel extends ViewModel {
<#list table.fields as field>
    /**
     * ${field.comment}
     */
    ${field.propertyName};
</#list>
<#list table.commonFields as field>
    /**
     * ${field.comment}
     */
    ${field.propertyName};
</#list>
}

=========================== 结束5==============================

=========================== ${table.entityName?uncap_first}/${table.entityName?uncap_first}-detail.component.ts 开始6==============================
import { Component, Injector } from '@angular/core';
import { DialogEditComponentBase } from '@utils';
import { ${table.entityName}ViewModel } from './model/${table.entityName?uncap_first}-view-model';

/**
 * 详情页
 */
@Component({
selector: '${table.entityName?uncap_first}-detail',
templateUrl: './html/detail.component.html'
})
export class ${table.entityName}DetailComponent extends DialogEditComponentBase<${table.entityName}ViewModel> {
    /**
     * 初始化详情页
     * @param injector 注入器
     */
    constructor(injector: Injector) {
        super(injector);
    }

    /**
     * 获取基地址
     */
    protected getBaseUrl() {
        return '${table.entityName?uncap_first}';
    }

    /**
     * 是否远程加载
     */
    isRequestLoad() {
        return false;
    }
}

=========================== 结束6==============================

=========================== ${table.entityName?uncap_first}/${table.entityName?uncap_first}-edit.component.ts 开始 7==============================
import { Component, Injector, OnInit } from '@angular/core';
import { DialogEditComponentBase } from '@utils';
import { ${table.entityName}ViewModel } from './model/${table.entityName?uncap_first}-view-model';

/**
 * 编辑页
 */
@Component({
selector: '${table.entityName?uncap_first}-edit',
templateUrl: './html/edit.component.html'
})
export class ${table.entityName}EditComponent extends DialogEditComponentBase<${table.entityName}ViewModel> implements OnInit {
    /**
     * 初始化编辑页
     * @param injector 注入器
     */
    constructor(injector: Injector) {
        super(injector);
    }

    ngOnInit(){
        super.ngOnInit();
    }

    /**
     * 获取基地址
     */
    protected getBaseUrl() {
        return '<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>';
    }

    /**
    * 是否远程加载
    */
    isRequestLoad() {
        return false;
    }
}

=========================== 结束7==============================

=========================== ${table.entityName?uncap_first}/${table.entityName?uncap_first}-list.component.ts 开始8==============================
import { Component, Injector } from '@angular/core';
import { TableQueryComponentBase } from '@utils';
import { ${table.entityName}Query } from './model/${table.entityName?uncap_first}-query';
import { ${table.entityName}ViewModel } from './model/${table.entityName?uncap_first}-view-model';
import { ${table.entityName}DetailComponent } from './${table.entityName?uncap_first}-detail.component';
import { ${table.entityName}EditComponent } from './${table.entityName?uncap_first}-edit.component';

/**
 * 列表页
 */
@Component({
selector: '${table.entityName?uncap_first}-list',
templateUrl: './html/index.component.html'
})
export class ${table.entityName}ListComponent extends TableQueryComponentBase<${table.entityName}ViewModel, ${table.entityName}Query>  {
    /**
     * 初始化列表页
     * @param injector 注入器
     */
    constructor(injector: Injector) {
        super(injector);
    }

    /**
     * 获取创建弹出框组件
     */
    getCreateDialogComponent() {
        return ${table.entityName}EditComponent;
    }

    /**
     * 获取详情弹出框组件
     */
    getDetailDialogComponent() {
        return ${table.entityName}DetailComponent;
    }

    getDialogWidth() {
        return '500px';
    }
}


=========================== 结束8==============================

=========================== XXX-routing.module 路由添加以下内容 开始9==============================
import { ${table.entityName}ListComponent } from './${table.entityName?uncap_first}/${table.entityName?uncap_first}-list.component';
{ path: '${table.entityName?uncap_first}', component: ${table.entityName}ListComponent, data: { title: '${table.comment!}管理' }, },
=========================== 结束 9==============================

=========================== XXX.module.ts 添加以下内容 开始 10====================================

import { ${table.entityName}DetailComponent } from './${table.entityName?uncap_first}/${table.entityName?uncap_first}-detail.component';
import { ${table.entityName}EditComponent } from './${table.entityName?uncap_first}/${table.entityName?uncap_first}-edit.component';
import { ${table.entityName}ListComponent } from './${table.entityName?uncap_first}/${table.entityName?uncap_first}-list.component';
    declarations: [
        ${table.entityName}ListComponent, ${table.entityName}EditComponent, ${table.entityName}DetailComponent,
    ],
    entryComponents: [
        ${table.entityName}EditComponent, ${table.entityName}DetailComponent,
    ]
=========================== 结束 10==============================
删除后端mapper文件夹下xml 文件夹及内容