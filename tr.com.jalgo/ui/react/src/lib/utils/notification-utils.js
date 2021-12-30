import { Toast } from 'primereact/toast';
import React from 'react';

 
 
const notificationUtils=()=>{
    const toast= React.createRef();
    const success=(message)=>{
        toast.current.show({severity: 'success', summary: 'Başarılı', detail: {message}});
    };
    const warn=(message)=>{
        toast.current.show({severity: 'warn', summary: 'Uyarı', detail: {message}});
    };
    const error=(message)=>{
        toast.current.show({severity: 'error', summary: 'Hata', detail: {message}});
    };
    const  info=(message)=>{
        toast.current.show({severity: 'info', summary: 'Bilgi', detail: {message}});
    };
    return(
        <Toast ref={(el) => toast = el} />
    );
}
export default notificationUtils;