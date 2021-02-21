<#import "profilePicture.ftl" as p>

<#macro modalPic picture width height>
  <div class="modal fade" id="bigPic" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <@p.profilePicture picture width height/>
        <#--                    <img src="https://storage.googleapis.com/communication-network/${message.filename}"-->
        <#--                         id="messagePicBig" class="rounded mx-auto mt-4 md-1" alt="No pic :("/>-->
    </div>
  </div>
</#macro>