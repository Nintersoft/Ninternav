//---------------------------------------------------------------------------

#include <fmx.h>
#pragma hdrstop

#include "Unit3.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.fmx"
TForm3 *Form3;
//---------------------------------------------------------------------------
// Declaração de variáveis -> Var declaration
int a=0;
//---------------------------------------------------------------------------
__fastcall TForm3::TForm3(TComponent* Owner)
	: TForm(Owner)
{
}
//---------------------------------------------------------------------------
void __fastcall TForm3::SpeedButton1Click(TObject *Sender)
{
	Form3->Hide();
}
//---------------------------------------------------------------------------
void __fastcall TForm3::Button1Click(TObject *Sender)
{
	ShowMessage("Ninternav por Nintersoft -> Todos os direitos reservados (2014)");
}
//---------------------------------------------------------------------------
void __fastcall TForm3::Label2Click(TObject *Sender)
{
	if (a<5) {
		a++;
	}
	else {
		ShowMessage("Parabéns, você descobriu este easter egg -> Seja o primeiro a notificar em nossa comunidade e você ganhará o nosso primeiro APP pago de graça!");
	}
}
//---------------------------------------------------------------------------
