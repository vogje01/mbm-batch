import React from 'react';
import {Popup} from "devextreme-react/popup";
import {Button} from "devextreme-react";
import FisPage from "../../components/FisPage";
import Form, {Item} from "devextreme-react/form";
import {updateItem} from "../../components/ServerConnection";

class JobGroupDetails extends FisPage {

    constructor(props) {
        super(props);
        this.state = {
            currentJobGroup: props.currentJobGroup,
            selectedIndex: 0
        };
        this.updateJobGroup = this.updateJobGroup.bind(this);
    }

    updateJobGroup() {
        updateItem(this.state.currentJobGroup._links.update.href, this.state.currentJobGroup, 'jobGroupDto')
            .then((data) => {
                this.state.currentJobGroup = data;
                this.props.closePopup();
            })
    }

    render() {
        const jobGroup = this.props.currentJobGroup;
        return (
            <React.Fragment>
                <Popup
                    id={'jobGroupDetailPopup'}
                    className={'popup'}
                    visible={true}
                    dragEnabled={true}
                    closeOnOutsideClick={true}
                    showTitle={true}
                    title={'Job Group Details: ' + (jobGroup.name)}
                    height={'auto'}
                    minWidth={500}
                    minHeight={480}
                    maxWidth={1200}
                    maxHeight={1200}
                    resizeEnabled={true}
                    onHiding={this.props.closePopup}>
                    <Form
                        id={'form'}
                        formData={jobGroup}
                        labelLocation={'top'}
                        minColWidth={200}
                        colCount={2}>
                        <Item dataField={'id'} editorOptions={{disabled: true}}/>
                        <Item dataField={'name'}/>
                        <Item dataField={'label'}/>
                        <Item dataField={'active'} editorType={'dxCheckBox'}/>
                        <Item dataField={'description'} colSpan={2} editorType={'dxTextArea'} editorOptions={{height: 90}}/>
                    </Form>
                    <Button
                        width={90}
                        text={'Apply'}
                        type={'success'}
                        stylingMode={'contained'}
                        onClick={this.updateJobGroup}
                        style={{float: 'right', margin: '5px 10px 10px 0'}}/>
                    <Button
                        width={90}
                        text={'Close'}
                        type={'success'}
                        stylingMode={'outlined'}
                        onClick={this.props.closePopup}
                        style={{float: 'right', margin: '5px 10px 10px 0'}}/>
                </Popup>
            </React.Fragment>
        );
    }
}

export default JobGroupDetails;