create procedure NoApadrinados @Filter varchar(MAX) as
  begin
    select j.id, j.fechaEntrada, aj.fecha_fin into #NoApadrinados from Jovenes J left join ApadrinarJoven AJ on J.id = AJ.joven_id
    where j.fechaBaja is null and j.id not in (
      select j.id from Jovenes J left join ApadrinarJoven AJ on J.id = AJ.joven_id
      where fecha_fin is null and AJ.id is not null group by j.id having max(aj.fecha_inicio) is not null);
    select j.* from Jovenes j inner join #NoApadrinados nap on nap.id = j.id where @Filter is null or
                                                                                   (j.nombre + ' ' + j.apellidos)
                                                                                     like '%' + @Filter + '%'
    order by fecha_fin, fechaEntrada;
  end