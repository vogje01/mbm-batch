import React from 'react';
import {DataGrid} from "devextreme-react";
import {Column, Editing, FilterRow, Pager, Paging, RemoteOperations, Selection} from "devextreme-react/data-grid";
import {userDataSource} from "./UserDataSource";
import UpdateTimer from "../../components/UpdateTimer";
import FisPage from "../../components/FisPage";
import {refreshSubject} from "../../components/MainComponent";
import {filter} from "rxjs/operators";

class UserView extends FisPage {

    constructor(props) {
        super(props);
        this.state = {
            currentUser: {},
            currentUsers: [],
            showDetails: false,
            selectedIndex: 0
        };
        this.toggleDetails = this.toggleDetails.bind(this);
        this.unsub = refreshSubject
            .pipe(filter(f => f.topic === 'Refresh'))
            .subscribe(() => this.setState({refreshLists: {}}));
    }

    componentWillUnmount() {
        this.unsub.unsubscribe()
    }

    shouldComponentUpdate(nextProps, nextState, nextContext) {
        userDataSource().reload();
        return true;
    }

    toggleDetails(e) {
        this.setState({
            showDetails: !this.state.showDetails,
            currentUser: e ? e.data : null
        });
    }

    render() {
        if (this.props.hidden) {
            return null;
        }
        return (
            <React.Fragment>
                <DataGrid
                    id={'UserTable'}
                    dataSource={userDataSource()}
                    hoverStateEnabled={true}
                    allowColumnReordering={true}
                    allowColumnResizing={true}
                    columnResizingMode={'widget'}
                    columnMinWidth={50}
                    columnAutoWidth={true}
                    showColumnLines={true}
                    showRowLines={true}
                    showBorders={true}
                    rowAlternationEnabled={true}>
                    <FilterRow visible={true}/>
                    <Selection mode={'single'}/>
                    <Editing
                        mode="form"
                        useIcons={true}
                        allowUpdating={true}
                        allowAdding={true}
                        allowDeleting={true}/>
                    <Column
                        caption={'UserID'}
                        dataField={'userId'}
                        allowEditing={true}
                        allowFiltering={true}
                        allowSorting={true}
                        allowReordering={true}/>
                    <Column
                        allowSorting={false}
                        allowReordering={false}
                        width={80}
                        type={'buttons'}
                        buttons={[
                            {
                                name: 'edit',
                                hint: 'Edit user',
                                icon: 'material-icons-outlined ic-edit md-18'
                            },
                            {
                                name: 'delete',
                                hint: 'Delete user',
                                icon: 'material-icons-outlined ic-delete md-18'
                            }
                        ]}/>
                    <RemoteOperations
                        sorting={true}
                        paging={true}/>
                    <Paging defaultPageSize={5}/>
                    <Pager allowedPageSizes={[5, 10, 20, 50, 100]} showPageSizeSelector={true}/>
                </DataGrid>*/
                <UpdateTimer/>
            </React.Fragment>
        );
    }
}

export default UserView;